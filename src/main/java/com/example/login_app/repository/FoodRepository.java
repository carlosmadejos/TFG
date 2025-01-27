package com.example.login_app.repository;

import com.example.login_app.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByNameContainingIgnoreCase(String name); // Para búsquedas
}
