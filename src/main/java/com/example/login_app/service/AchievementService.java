package com.example.login_app.service;

import com.example.login_app.model.*;
import com.example.login_app.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final ProgressRepository progressRepository;
    private final FoodRepository foodRepository;
    private final ExerciseRepository exerciseRepository;

    public AchievementService(
            AchievementRepository achievementRepository,
            UserAchievementRepository userAchievementRepository,
            ProgressRepository progressRepository,
            FoodRepository foodRepository,
            ExerciseRepository exerciseRepository) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.progressRepository = progressRepository;
        this.foodRepository = foodRepository;
        this.exerciseRepository = exerciseRepository;
    }

    /**
     * Obtiene todos los logros definidos en el sistema.
     */
    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    /**
     * Obtiene los logros ya alcanzados por el usuario.
     */
    public List<UserAchievement> getUserAchievements(User user) {
        return userAchievementRepository.findByUser(user);
    }

    @PostConstruct
    public void initAchievements() {
        createIfNotExists("FIRST_FOOD_LOG",   "¡Empezando a comer!",      "Has añadido tu primera comida al diario",           1.0,  Achievement.Level.BRONZE);
        createIfNotExists("FIRST_EXERCISE_LOG","Ponte en movimiento",      "Has registrado tu primer ejercicio",                1.0,  Achievement.Level.BRONZE);
        createIfNotExists("CALORIE_GOAL",      "Objetivo cumplido",        "Tu ingesta no ha superado tu meta calórica",        0.0,  Achievement.Level.SILVER);
        createIfNotExists("DAILY_LOG_COMPLETE","Día completado",           "Has cerrado tu registro del día",                   0.0,  Achievement.Level.SILVER);
        createIfNotExists("WEIGHT_STREAK_7",   "Constancia semanal",       "Has actualizado tu peso cada día durante una semana", 7.0, Achievement.Level.GOLD);
        createIfNotExists("FIRST_PLAN_FINISH", "Plan cumplido",            "Has visitado la página de detalles de un plan",     1.0,  Achievement.Level.BRONZE);
        createIfNotExists("FOOD_SEARCHER",     "Explorador de comidas",    "Has buscado alimentos 5 veces",                     5.0, Achievement.Level.SILVER);
    }

    private void createIfNotExists(String code, String name, String desc, Double threshold, Achievement.Level lvl) {
        if (achievementRepository.findByCode(code).isEmpty()) {
            Achievement a = new Achievement(code, name, desc, threshold, lvl);
            achievementRepository.save(a);
        }
    }

    /**
     * Evalúa y asigna logros de pérdida de peso al usuario.
     */
    @Transactional
    public void evaluateWeightLossAchievements(User user) {
        // 1) Se obtiene todos los registros de progreso
        var progresses = progressRepository.findByUser(user);

        // 2) Calcula el peso inicial:
        // Si hay registros, toma el más antiguo.
        // Si no, usa el peso actual almacenado.
        double initialWeight = progresses.stream()
                .min(Comparator.comparing(Progress::getDate))
                .map(Progress::getWeight)
                .orElse(user.getWeight());

        // 3) Peso actual y pérdida
        double currentWeight = user.getWeight();
        double weightLost = initialWeight - currentWeight;
        if (weightLost <= 0) {
            return; // No hay pérdida de peso
        }

        // 4) Filtra y ordena los logros de pérdida de peso
        List<Achievement> weightLossAchievements = achievementRepository.findAll().stream()
                .filter(a -> a.getCode().startsWith("WEIGHT_LOSS"))
                .sorted(Comparator.comparing(Achievement::getThreshold))
                .collect(Collectors.toList());

        // 5) Asigna cada logro alcanzado
        for (Achievement achievement : weightLossAchievements) {
            if (weightLost >= achievement.getThreshold()) {
                boolean exists = userAchievementRepository
                        .findByUserAndAchievement(user, achievement)
                        .isPresent();
                if (!exists) {
                    var ua = new UserAchievement();
                    ua.setUser(user);
                    ua.setAchievement(achievement);
                    ua.setAchievedAt(LocalDateTime.now());
                    userAchievementRepository.save(ua);
                }
            }
        }
    }

    @Transactional
    public void evaluateFirstFoodLog(User user, DailyLog dailyLog) {
        if (!dailyLog.getFoods().isEmpty()) {
            awardIfMissing(user, "FIRST_FOOD_LOG");
        }
    }

    @Transactional
    public void evaluateFirstExerciseLog(User user) {
        if (!exerciseRepository.findByUser(user).isEmpty()) {
            awardIfMissing(user, "FIRST_EXERCISE_LOG");
        }
    }

    @Transactional
    public void evaluateCalorieGoal(User user, DailyLog dailyLog) {
        if (dailyLog.getTotalCalories() <= dailyLog.getCalorieGoal()) {
            awardIfMissing(user, "CALORIE_GOAL");
        }
    }

    @Transactional
    public void evaluateDailyLogComplete(User user, DailyLog dailyLog) {
        if (dailyLog.isClosed()) {
            awardIfMissing(user, "DAILY_LOG_COMPLETE");
        }
    }

    @Transactional
    public void evaluateWeightStreak(User user) {
        List<Progress> list = progressRepository.findByUser(user).stream()
                .sorted(Comparator.comparing(Progress::getDate).reversed())
                .limit(7)
                .collect(Collectors.toList());
        if (list.size() >= 7 && areConsecutiveDays(list)) {
            awardIfMissing(user, "WEIGHT_STREAK_7");
        }
    }

    @Transactional
    public void evaluateFirstPlanFinish(User user) {
        awardIfMissing(user, "FIRST_PLAN_FINISH");
    }

    @Transactional
    public void evaluateFoodSearcher(User user, long count) {
        if (count >= 5) {
            awardIfMissing(user, "FOOD_SEARCHER");
        }
    }

    private void awardIfMissing(User user, String code) {
        Achievement a = achievementRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Achievement not found: " + code));
        if (userAchievementRepository
                .findByUserAndAchievement(user, a)
                .isEmpty()) {
            UserAchievement ua = new UserAchievement();
            ua.setUser(user);
            ua.setAchievement(a);
            ua.setAchievedAt(LocalDateTime.now());
            userAchievementRepository.save(ua);
        }
    }

    private boolean areConsecutiveDays(List<Progress> p) {
        LocalDate prev = p.get(0).getDate();
        for (int i = 1; i < p.size(); i++) {
            if (!p.get(i).getDate().equals(prev.minusDays(1))) {
                return false;
            }
            prev = p.get(i).getDate();
        }
        return true;
    }
}
