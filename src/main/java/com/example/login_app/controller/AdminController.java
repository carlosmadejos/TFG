package com.example.login_app.controller;

import com.example.login_app.model.User;
import com.example.login_app.repository.UserRepository;
import com.example.login_app.repository.ProgressRepository;
import com.example.login_app.repository.ExerciseRepository;
import com.example.login_app.repository.DailyLogRepository;
import com.example.login_app.repository.TrainingPlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")  // Solo los ADMIN pueden acceder a este controlador
public class AdminController {

    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;
    private final ExerciseRepository exerciseRepository;
    private final DailyLogRepository dailyLogRepository;
    private final TrainingPlanRepository trainingPlanRepository;


    public AdminController(UserRepository userRepository, ProgressRepository progressRepository, ExerciseRepository exerciseRepository, DailyLogRepository dailyLogRepository, TrainingPlanRepository trainingPlanRepository) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.exerciseRepository = exerciseRepository;
        this.dailyLogRepository = dailyLogRepository;
        this.trainingPlanRepository = trainingPlanRepository;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        model.addAttribute("user", user); // Usuario actual
        model.addAttribute("users", userRepository.findAll()); // Todos los usuarios para administración

        return "admin";
    }

    @DeleteMapping("/delete-user/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            // Verificar si el usuario existe antes de eliminar
            if (!userRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("El usuario no existe.");
            }

            if (userRepository.findById(id).get().getRole().equals("ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes eliminar a otro administrador.");
            }

            // Eliminar registros relacionados en otras tablas antes de eliminar al usuario
            progressRepository.deleteByUserId(id);
            exerciseRepository.deleteByUserId(id);
            dailyLogRepository.deleteByUserId(id);

            // Finalmente, eliminar el usuario
            userRepository.deleteById(id);

            return ResponseEntity.ok("Usuario y registros eliminados correctamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    @GetMapping("/training-plans")
    public String manageTrainingPlans(Model model) {
        model.addAttribute("trainingPlans", trainingPlanRepository.findAll());
        return "admin-training-plans";  // Carga la vista exclusiva para admins
    }
}
