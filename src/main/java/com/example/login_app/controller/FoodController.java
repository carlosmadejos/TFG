package com.example.login_app.controller;

import com.example.login_app.service.FoodDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodDataService foodDataService;

    public FoodController(FoodDataService foodDataService) {
        this.foodDataService = foodDataService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchFoods(@RequestParam String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "La búsqueda no puede estar vacía."));
        }

        try {
            List<Map<String, Object>> foods = foodDataService.searchFoods(query.trim());

            if (foods.isEmpty()) {
                return ResponseEntity.ok(Map.of("message", "No se encontraron alimentos."));
            }

            return ResponseEntity.ok(Map.of("results", foods));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Error al obtener los alimentos.",
                    "details", e.getMessage()
            ));
        }
    }
}
