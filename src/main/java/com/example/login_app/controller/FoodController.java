package com.example.login_app.controller;

import com.example.login_app.service.FoodDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodDataService foodDataService;

    // Buscar alimentos utilizando la API externa
    @GetMapping("/search")
    public Map<String, Object> searchFoods(
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return foodDataService.searchFoods(query, limit, offset);
    }
}
