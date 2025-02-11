package com.example.login_app.controller;

import com.example.login_app.model.DailyLog;
import com.example.login_app.model.Food;
import com.example.login_app.model.User;
import com.example.login_app.repository.DailyLogRepository;
import com.example.login_app.repository.FoodRepository;
import com.example.login_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/daily-log")
public class DailyLogController {

    @Autowired
    private DailyLogRepository dailyLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    // Obtener el registro diario del usuario autenticado con alimentos
    @GetMapping
    public ResponseEntity<?> getDailyLog(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DailyLog dailyLog = dailyLogRepository.findByUser(user)
                .orElse(new DailyLog());
        dailyLog.setUser(user);

        return ResponseEntity.ok(dailyLog);
    }

    // Agregar un alimento al registro diario
    @PostMapping("/add-food")
    public ResponseEntity<?> addFoodToDailyLog(@RequestBody Food foodRequest, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DailyLog dailyLog = dailyLogRepository.findByUser(user).orElseGet(() -> {
            DailyLog newLog = new DailyLog();
            newLog.setUser(user);
            newLog.setCalorieGoal(2000);
            return dailyLogRepository.save(newLog);
        });

        Food food = new Food();
        food.setName(foodRequest.getName());
        food.setCalories(foodRequest.getCalories());
        food.setProteins(foodRequest.getProteins());
        food.setCarbs(foodRequest.getCarbs());
        food.setFats(foodRequest.getFats());
        food.setImageUrl(foodRequest.getImageUrl());

        food.setDailyLog(dailyLog);
        foodRepository.save(food);

        return ResponseEntity.ok("Alimento agregado exitosamente");
    }


    // Eliminar un alimento del registro diario
    @DeleteMapping("/remove-food/{foodId}")
    public ResponseEntity<?> removeFoodFromDailyLog(@PathVariable Long foodId, Principal principal) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado"));

        foodRepository.delete(food);
        return ResponseEntity.ok("Alimento eliminado exitosamente");
    }

    // Eliminar el registro diario
    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deleteDailyLog(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        dailyLogRepository.deleteByUser(user);

        return ResponseEntity.ok("Registro diario eliminado exitosamente");
    }
}
