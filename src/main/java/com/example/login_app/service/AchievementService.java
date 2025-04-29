package com.example.login_app.service;

import com.example.login_app.model.Achievement;
import com.example.login_app.model.User;
import com.example.login_app.model.UserAchievement;
import com.example.login_app.repository.AchievementRepository;
import com.example.login_app.repository.ProgressRepository;
import com.example.login_app.repository.UserAchievementRepository;
import com.example.login_app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.login_app.model.Progress;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final ProgressRepository progressRepository;

    public AchievementService(AchievementRepository achievementRepository,
                              UserAchievementRepository userAchievementRepository,
                              ProgressRepository progressRepository) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.progressRepository = progressRepository;
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
}
