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
    public Exercise saveExercise(@RequestBody Exercise exercise, Principal principal) {
        if (exercise.getDate() == null || exercise.getDescription() == null || exercise.getDescription().isEmpty()) {
            throw new RuntimeException("La fecha o la descripción del ejercicio no pueden estar vacías");
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        exercise.setUser(user);
        return exerciseRepository.save(exercise); // Devolver el ejercicio guardado
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
                public final Long id = exercise.getId(); // Incluir el ID del ejercicio
                public final String title = exercise.getDescription();
                public final String start = exercise.getDate().toString(); // FullCalendar usa el formato ISO-8601
            };
        }).collect(Collectors.toList());
    }

    // Eliminar un ejercicio
    @DeleteMapping("/delete/{id}")
    public String deleteExercise(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        if (!exercise.getUser().equals(user)) {
            throw new RuntimeException("No tienes permiso para eliminar este ejercicio");
        }

        exerciseRepository.delete(exercise);
        return "Ejercicio eliminado correctamente";
    }

}
