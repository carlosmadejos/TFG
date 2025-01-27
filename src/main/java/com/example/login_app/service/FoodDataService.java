package com.example.login_app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class FoodDataService {

    private static final String API_URL = "https://api.nal.usda.gov/fdc/v1/foods/search";
    private static final String API_KEY = "8ygmFRXYYdzVq6Tch6rpqBOCzAYIaKV7E3BHGjCj";

    public Map<String, Object> searchFoods(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("api_key", API_KEY)
                .queryParam("query", query)
                .toUriString();

        return restTemplate.getForObject(url, Map.class);
    }
}
