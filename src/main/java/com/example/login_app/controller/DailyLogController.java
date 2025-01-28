package com.example.login_app.controller;

import com.example.login_app.model.DailyLog;
import com.example.login_app.model.User;
import com.example.login_app.repository.DailyLogRepository;
import com.example.login_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/daily-log")
public class DailyLogController {

    @Autowired
    private DailyLogRepository dailyLogRepository;

    @Autowired
    private UserRepository userRepository;

    // Obtener el registro diario del usuario autenticado
    @GetMapping
    public ResponseEntity<?> getDailyLog(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DailyLog dailyLog = dailyLogRepository.findByUser(user)
                .orElse(new DailyLog()); // Retornar un nuevo registro si no existe
        dailyLog.setUser(user); // Asegurarse de que el usuario esté vinculado

        return ResponseEntity.ok(dailyLog);
    }

    // Guardar o actualizar el registro diario
    @PostMapping
    public ResponseEntity<?> saveDailyLog(@RequestBody DailyLog dailyLogRequest, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DailyLog dailyLog = dailyLogRepository.findByUser(user).orElse(new DailyLog());
        dailyLog.setUser(user);
        dailyLog.setCalorieGoal(dailyLogRequest.getCalorieGoal());
        dailyLog.setDailyLogJson(dailyLogRequest.getDailyLogJson());
        dailyLogRepository.save(dailyLog);

        return ResponseEntity.ok("Registro diario guardado exitosamente");
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
