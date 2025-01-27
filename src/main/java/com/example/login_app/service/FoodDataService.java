package com.example.login_app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class FoodDataService {

    private static final String API_KEY = "8ygmFRXYYdzVq6Tch6rpqBOCzAYIaKV7E3BHGjCj";
    private static final String API_URL = "https://api.nal.usda.gov/fdc/v1/foods/search";

    public Map<String, Object> searchFoods(String query, int limit, int offset) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?api_key=%s&query=%s&pageSize=%d&pageNumber=%d",
                API_URL, API_KEY, query, limit, offset / limit + 1);
        return restTemplate.getForObject(url, Map.class);
    }
}
