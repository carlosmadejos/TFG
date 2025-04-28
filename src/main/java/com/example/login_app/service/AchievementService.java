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
        // Obtener el progreso inicial (peso al primer registro)
        var progresses = progressRepository.findByUser(user);
        if (progresses.isEmpty()) {
            return;
        }
        var initialProgress = progresses.stream()
                .min(Comparator.comparing(p -> p.getDate()))
                .orElseThrow();
        double initialWeight = initialProgress.getWeight();

        // Peso actual del usuario
        double currentWeight = user.getWeight();
        double weightLost = initialWeight - currentWeight;
        if (weightLost <= 0) {
            return; // No hay pérdida de peso
        }

        // Filtrar logros de tipo pérdida de peso y ordenar por threshold ascendente
        List<Achievement> weightLossAchievements = achievementRepository.findAll().stream()
                .filter(a -> a.getCode().startsWith("WEIGHT_LOSS"))
                .sorted(Comparator.comparing(Achievement::getThreshold))
                .collect(Collectors.toList());

        // Evalúa cada logro
        for (Achievement achievement : weightLossAchievements) {
            if (weightLost >= achievement.getThreshold()) {
                // Verificar si el usuario ya tiene este logro
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
