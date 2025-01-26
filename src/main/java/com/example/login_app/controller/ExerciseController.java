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

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public String saveExercise(@RequestBody Exercise exercise, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        exercise.setUser(user);
        exerciseRepository.save(exercise);
        return "Ejercicio guardado correctamente";
    }

    @GetMapping("/get")
    public List<Exercise> getExercises(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return exerciseRepository.findByUser(user);
    }

}
