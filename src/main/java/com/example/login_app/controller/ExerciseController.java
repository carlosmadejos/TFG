package com.example.login_app.controller;

import com.example.login_app.model.Exercise;
import com.example.login_app.model.User;
import com.example.login_app.repository.ExerciseRepository;
import com.example.login_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    // Guardar un ejercicio
    @PostMapping("/save")
    public String saveExercise(@RequestBody Exercise exercise, Principal principal) {
        if (exercise.getDate() == null || exercise.getDescription() == null || exercise.getDescription().isEmpty()) {
            return "La fecha o la descripción del ejercicio no pueden estar vacías";
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        exercise.setUser(user);
        exerciseRepository.save(exercise);
        return "Ejercicio guardado correctamente";
    }

    // Obtener ejercicios en formato compatible con FullCalendar
    @GetMapping("/get")
    public List<Object> getExercises(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Exercise> exercises = exerciseRepository.findByUser(user);

        // Convertir ejercicios a formato JSON compatible con FullCalendar
        return exercises.stream().map(exercise -> {
            return new Object() {
                public final String title = exercise.getDescription();
                public final String start = exercise.getDate().toString(); // FullCalendar usa el formato ISO-8601
            };
        }).collect(Collectors.toList());
    }
}
