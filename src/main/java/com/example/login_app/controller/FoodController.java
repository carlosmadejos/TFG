package com.example.login_app.controller;

import com.example.login_app.service.FoodDataService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Map<String, Object>> searchFoods(@RequestParam String query) {
        return foodDataService.searchFoods(query);
    }

}
