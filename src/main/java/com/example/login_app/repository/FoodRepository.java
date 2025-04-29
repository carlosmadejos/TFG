package com.example.login_app.repository;

import com.example.login_app.model.DailyLog;
import com.example.login_app.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByNameContainingIgnoreCase(String name); // Para búsquedas
    List<Food> findByDailyLog(DailyLog dailyLog);

    @Transactional
    @Modifying
    @Query("DELETE FROM Food f WHERE f.dailyLog.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
