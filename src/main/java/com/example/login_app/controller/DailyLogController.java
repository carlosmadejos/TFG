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
import java.util.Optional;

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
    @GetMapping("/{id}")
    public ResponseEntity<?> getDailyLogById(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar solo los registros del usuario autenticado
        Optional<DailyLog> dailyLog = dailyLogRepository.findById(id);

        if (dailyLog.isEmpty() || !dailyLog.get().getUser().equals(user)) {
            return ResponseEntity.status(403).body("No tienes permiso para ver este registro.");
        }

        List<Food> foods = foodRepository.findByDailyLog(dailyLog.get());
        dailyLog.get().setFoods(foods);

        return ResponseEntity.ok(dailyLog.get());
    }



    // Agregar un alimento al registro diario
    @PostMapping("/add-food")
    public ResponseEntity<?> addFoodToDailyLog(@RequestBody Food foodRequest, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Intentar obtener un DailyLog activo o crear uno nuevo si no existe
        DailyLog dailyLog = dailyLogRepository.findFirstByUserAndClosedFalseOrderByIdDesc(user)
                .orElseGet(() -> {
                    System.out.println("No se encontró un DailyLog activo, creando uno nuevo...");
                    DailyLog newLog = new DailyLog();
                    newLog.setUser(user);
                    newLog.setCalorieGoal(2000);
                    newLog.setClosed(false);
                    return dailyLogRepository.save(newLog); // Guardar el nuevo registro
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
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado"));

        // Verificar que el alimento pertenece a un DailyLog del usuario autenticado
        if (!food.getDailyLog().getUser().equals(user)) {
            return ResponseEntity.status(403).body("No tienes permiso para eliminar este alimento.");
        }

        foodRepository.delete(food);
        return ResponseEntity.ok("Alimento eliminado exitosamente");
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity<?> closeDailyLog(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<DailyLog> dailyLogOpt = dailyLogRepository.findFirstByUserAndClosedFalseOrderByIdDesc(user);
        if (dailyLogOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No hay un registro diario activo.");
        }
        DailyLog dailyLog = dailyLogOpt.get();


        dailyLog.setClosed(true);
        dailyLogRepository.save(dailyLog);

        return ResponseEntity.ok("Registro diario cerrado exitosamente");
    }


    @PostMapping("/update-calorie-goal")
    public ResponseEntity<?> updateCalorieGoal(@RequestBody DailyLog updatedLog, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DailyLog dailyLog = dailyLogRepository.findByUser(user)
                .orElseGet(() -> {
                    DailyLog newLog = new DailyLog();
                    newLog.setUser(user);
                    newLog.setCalorieGoal(2000);
                    return dailyLogRepository.save(newLog);
                });

        dailyLog.setCalorieGoal(updatedLog.getCalorieGoal());
        dailyLogRepository.save(dailyLog);

        return ResponseEntity.ok("Objetivo calórico actualizado correctamente");
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewDailyLog(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si ya hay un registro activo
        Optional<DailyLog> activeLog = dailyLogRepository.findFirstByUserAndClosedFalseOrderByIdDesc(user);

        if (activeLog.isPresent()) {
            return ResponseEntity.badRequest().body("Ya tienes un registro activo.");
        }

        DailyLog newLog = new DailyLog();
        newLog.setUser(user);
        newLog.setCalorieGoal(2000); // Default
        newLog.setClosed(false); // Marcarlo como activo
        dailyLogRepository.save(newLog);

        return ResponseEntity.ok(newLog);
    }



    @GetMapping("/history")
    public ResponseEntity<List<DailyLog>> getDailyLogHistory(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<DailyLog> logs = dailyLogRepository.findByUserAndClosedTrue(user);
        return ResponseEntity.ok(logs);
    }

    @PutMapping("/close")
    public ResponseEntity<?> closeCurrentDailyLog(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<DailyLog> activeLog = dailyLogRepository.findFirstByUserAndClosedFalseOrderByIdDesc(user);

        if (activeLog.isPresent()) {
            DailyLog log = activeLog.get();
            log.setClosed(true);
            dailyLogRepository.save(log);
            return ResponseEntity.ok("Registro cerrado correctamente.");
        }

        return ResponseEntity.badRequest().body("No hay un registro activo para cerrar.");
    }

    @GetMapping
    public ResponseEntity<?> getActiveDailyLog(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<DailyLog> dailyLogOpt = dailyLogRepository.findFirstByUserAndClosedFalseOrderByIdDesc(user);

        if (dailyLogOpt.isEmpty()) {
            System.out.println("No se encontró un DailyLog activo, creando uno nuevo..."); // Log para depuración

            DailyLog newLog = new DailyLog();
            newLog.setUser(user);
            newLog.setCalorieGoal(2000);
            newLog.setClosed(false);

            dailyLogRepository.save(newLog);

            return ResponseEntity.ok(newLog);
        }

        DailyLog dailyLog = dailyLogOpt.get();
        List<Food> foods = foodRepository.findByDailyLog(dailyLog);
        dailyLog.setFoods(foods);

        return ResponseEntity.ok(dailyLog);
    }





}
