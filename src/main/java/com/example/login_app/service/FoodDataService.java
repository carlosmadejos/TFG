package com.example.login_app.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodDataService {

    private static final String OPEN_FOOD_FACTS_API_URL = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=%s&json=1";

    @Cacheable(value = "foodSearchCache", key = "#query", unless = "#result == null || #result.isEmpty()")
    public List<Map<String, Object>> searchFoods(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(OPEN_FOOD_FACTS_API_URL, query);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        // Validar si la respuesta es vacía
        if (response.getBody() == null || response.getBody().trim().isEmpty()) {
            return List.of(Map.of("error", "No se obtuvo respuesta de Open Food Facts."));
        }

        // Convertir la respuesta JSON
        JSONObject jsonResponse = new JSONObject(response.getBody());
        return extractNutritionalInfo(jsonResponse, query);
    }

    private List<Map<String, Object>> extractNutritionalInfo(JSONObject json, String query) {
        List<Map<String, Object>> exactMatches = new ArrayList<>();
        List<Map<String, Object>> partialMatches = new ArrayList<>();

        try {
            JSONArray products = json.optJSONArray("products");
            if (products == null || products.isEmpty()) {
                return List.of(Map.of("error", "No se encontraron datos nutricionales para este alimento."));
            }

            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                String name = product.optString("product_name", "").trim();
                String imageUrl = product.optString("image_url", "");

                JSONObject nutrients = product.optJSONObject("nutriments");
                if (name.isEmpty() || nutrients == null) continue; // Filtrar productos sin nombre o sin nutrientes

                double calories = nutrients.optDouble("energy-kcal_100g", 0);
                double proteins = nutrients.optDouble("proteins_100g", 0);
                double carbs = nutrients.optDouble("carbohydrates_100g", 0);
                double fats = nutrients.optDouble("fat_100g", 0);

                // Filtrar alimentos sin calorías ni macronutrientes
                if (calories == 0 && proteins == 0 && carbs == 0 && fats == 0) continue;

                Map<String, Object> result = new HashMap<>();
                result.put("name", name);
                result.put("image_url", imageUrl);
                result.put("calories", calories);
                result.put("proteins", proteins);
                result.put("carbs", carbs);
                result.put("fats", fats);

                // Priorizar coincidencias exactas y parciales
                if (name.equalsIgnoreCase(query)) {
                    exactMatches.add(result);
                } else {
                    partialMatches.add(result);
                }
            }

        } catch (Exception e) {
            return List.of(Map.of("error", "Error procesando la respuesta de Open Food Facts."));
        }

        // Priorizar coincidencias exactas primero y luego las parciales
        exactMatches.addAll(partialMatches);
        return exactMatches;
    }
}