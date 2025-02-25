package com.example.login_app.controller;

import com.example.login_app.model.DailyLog;
import com.example.login_app.model.Food;
import com.example.login_app.model.User;
import com.example.login_app.repository.DailyLogRepository;
import com.example.login_app.repository.FoodRepository;
import com.example.login_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

        // Obtener el DailyLog activo o crear uno nuevo
        DailyLog dailyLog = dailyLogRepository.findFirstByUserAndClosedFalseOrderByIdDesc(user)
                .orElseGet(() -> {
                    DailyLog newLog = new DailyLog();
                    newLog.setUser(user);
                    newLog.setCalorieGoal(2000); // Valor por defecto
                    newLog.setTotalCalories(0);
                    newLog.setTotalProteins(0);
                    newLog.setTotalCarbs(0);
                    newLog.setTotalFats(0);
                    newLog.setClosed(false);
                    return dailyLogRepository.save(newLog);
                });

        // Obtener los gramos consumidos
        int gramsConsumed = foodRequest.getGramsConsumed() > 0 ? foodRequest.getGramsConsumed() : 100;
        double factor = gramsConsumed / 100.0;

        // Ajustar los valores nutricionales en función de los gramos consumidos
        int adjustedCalories = (int) (foodRequest.getCalories());
        int adjustedProteins = (int) (foodRequest.getProteins());
        int adjustedCarbs = (int) (foodRequest.getCarbs());
        int adjustedFats = (int) (foodRequest.getFats());

        // Crear y guardar el alimento
        Food food = new Food();
        food.setName(foodRequest.getName());
        food.setCalories(adjustedCalories);
        food.setProteins(adjustedProteins);
        food.setCarbs(adjustedCarbs);
        food.setFats(adjustedFats);
        food.setGramsConsumed(gramsConsumed);
        food.setImageUrl(foodRequest.getImageUrl());
        food.setDailyLog(dailyLog);

        foodRepository.save(food);

        // Actualizar los valores totales en el DailyLog
        dailyLog.setTotalCalories(dailyLog.getTotalCalories() + adjustedCalories);
        dailyLog.setTotalProteins(dailyLog.getTotalProteins() + adjustedProteins);
        dailyLog.setTotalCarbs(dailyLog.getTotalCarbs() + adjustedCarbs);
        dailyLog.setTotalFats(dailyLog.getTotalFats() + adjustedFats);

        dailyLogRepository.save(dailyLog);

        return ResponseEntity.ok("Alimento agregado y nutrientes actualizados.");
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


    @PutMapping("/update-calorie-goal")
    public ResponseEntity<?> updateCalorieGoal(@RequestBody DailyLog updatedLog, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar el DailyLog específico por ID y Usuario
        DailyLog dailyLog = dailyLogRepository.findByIdAndUser(updatedLog.getId(), user)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        dailyLog.setCalorieGoal(updatedLog.getCalorieGoal());
        dailyLog.setUpdatedAt(LocalDateTime.now());

        dailyLogRepository.save(dailyLog);

        return ResponseEntity.ok("Objetivo calórico actualizado correctamente");
    }




    @PostMapping("/new")
    public ResponseEntity<?> createNewDailyLog(@RequestBody Map<String, Object> requestBody,
                                               Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si ya hay un registro activo
        Optional<DailyLog> activeLog = dailyLogRepository.findFirstByUserAndClosedFalseOrderByIdDesc(user);

        if (activeLog.isPresent()) {
            return ResponseEntity.badRequest().body("Ya tienes un registro activo.");
        }

        // Leer el calorieGoal desde el requestBody y convertirlo correctamente
        int calorieGoal = 2000; // Valor por defecto
        if (requestBody.get("calorieGoal") != null) {
            try {
                calorieGoal = Integer.parseInt(requestBody.get("calorieGoal").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("El objetivo calórico debe ser un número válido.");
            }
        }

        // Crear nuevo registro con el objetivo calórico proporcionado
        DailyLog newLog = new DailyLog();
        newLog.setUser(user);
        newLog.setCalorieGoal(calorieGoal);
        newLog.setTotalCalories(0);
        newLog.setClosed(false); // Registro activo
        dailyLogRepository.save(newLog);

        return ResponseEntity.ok(newLog);
    }


    @GetMapping("/history")
    public ResponseEntity<List<DailyLog>> getDailyLogHistory(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        //List<DailyLog> logs = dailyLogRepository.findByUserAndClosedTrue(user);
        List<DailyLog> logs = dailyLogRepository.findByUserOrderByCreatedAtDesc(user);
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

        return ResponseEntity.ok(Map.of(
                "id", dailyLog.getId(),
                "calorieGoal", dailyLog.getCalorieGoal(),
                "totalCalories", dailyLog.getTotalCalories(),
                "totalProteins", dailyLog.getTotalProteins(),
                "totalCarbs", dailyLog.getTotalCarbs(),
                "totalFats", dailyLog.getTotalFats(),
                "foods", foods
        ));
    }

}
