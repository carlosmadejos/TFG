package com.example.login_app.controller;

import com.example.login_app.model.Achievement;
import com.example.login_app.model.Progress;
import com.example.login_app.model.UserAchievement;
import com.example.login_app.model.User;
import com.example.login_app.repository.ProgressRepository;
import com.example.login_app.repository.UserRepository;
import com.example.login_app.service.AchievementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AchievementsController {

    private final AchievementService achievementService;
    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;

    public AchievementsController(
            AchievementService achievementService,
            UserRepository userRepository,
            ProgressRepository progressRepository) {
        this.achievementService = achievementService;
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
    }

    @GetMapping("/profile/achievements")
    public String viewAchievements(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // 1) Reevalúa y asigna badges
        List<Progress> progresses = progressRepository.findByUser(user);
        if (!progresses.isEmpty()) {
            achievementService.evaluateWeightLossAchievements(user);
        }


        // 2) Calcula cuánto ha perdido el usuario
        double weightLost = 0;
        if (!progresses.isEmpty()) {
            Optional<Progress> first = progresses.stream()
                    .min(Comparator.comparing(Progress::getDate));
            if (first.isPresent()) {
                double initialWeight = first.get().getWeight();
                weightLost = initialWeight - user.getWeight();
            }
        }

        // 3) Vuelca al modelo
        model.addAttribute("user", user);
        model.addAttribute("allAchievements", achievementService.getAllAchievements());
        model.addAttribute("userAchievements",
                achievementService.getUserAchievements(user)
                        .stream()
                        .map(UserAchievement::getAchievement)
                        .toList());
        model.addAttribute("weightLost", weightLost);

        return "achievements";
    }

}
