package com.example.login_app.controller;

import com.example.login_app.service.FoodDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodDataService foodDataService;

    // Endpoint para buscar alimentos usando Open Food Facts
    @GetMapping("/search")
    public ResponseEntity<?> searchFoods(@RequestParam String query) {
        try {
            List<Map<String, Object>> foods = foodDataService.searchFoods(query);
            if (foods.isEmpty()) {
                return ResponseEntity.ok("No se encontraron alimentos.");
            }
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los alimentos.");
        }
    }


}
