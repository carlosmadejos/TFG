package com.example.login_app.controller;

import com.example.login_app.service.FoodDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import com.example.login_app.model.User;
import com.example.login_app.repository.UserRepository;
import com.example.login_app.service.AchievementService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodDataService foodDataService;
    private final UserRepository userRepository;
    private final AchievementService achievementService;

    public FoodController(
            FoodDataService foodDataService,
            UserRepository userRepository,
            AchievementService achievementService) {
        this.foodDataService = foodDataService;
        this.userRepository = userRepository;
        this.achievementService = achievementService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchFoods(
            @RequestParam String query,
            Principal principal               // <<<
    ) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "La búsqueda no puede estar vacía."));
        }

        // 1) Recupera al usuario
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2) Incrementa y guarda el contador de búsquedas
        int newCount = user.getSearchCount() + 1;
        user.setSearchCount(newCount);
        userRepository.save(user);

        // 3) Ejecuta la búsqueda
        try {
            List<Map<String, Object>> foods = foodDataService.searchFoods(query.trim());

            // 4) Evalúa el logro FOOD_SEARCHER
            achievementService.evaluateFoodSearcher(user, newCount);

            if (foods.isEmpty()) {
                return ResponseEntity.ok(Map.of("message", "No se encontraron alimentos."));
            }
            return ResponseEntity.ok(Map.of("results", foods));

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of(
                            "error",   "Error al obtener los alimentos.",
                            "details", e.getMessage()
                    ));
        }
    }
}
