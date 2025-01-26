package com.example.login_app.controller;

import com.example.login_app.model.Progress;
import com.example.login_app.model.User;
import com.example.login_app.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import java.security.Principal;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        // Devuelve la vista del login (login.html en templates)
        return "login";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Crear un objeto User vacío con valores iniciales
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor corrija los errores en el formulario");
            return "register";
        }

        // Verificar si el usuario ya existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "El nombre de usuario ya está en uso");
            return "register";
        }

        // Verificar si el email ya está en uso
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "El email ya está en uso");
            return "register";
        }

        // Asignar rol por defecto
        user.setRole("USER");

        // Encriptar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Guardar en la base de datos
        userRepository.save(user);

        // Redirigir al login con un mensaje de éxito
        model.addAttribute("success", "Usuario registrado exitosamente. Ahora puedes iniciar sesión.");
        return "redirect:/login";
    }







}
