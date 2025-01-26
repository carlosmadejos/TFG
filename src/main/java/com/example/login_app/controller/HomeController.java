package com.example.login_app.controller;

import com.example.login_app.model.User;
import com.example.login_app.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        // Obtener el usuario autenticado
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Pasar el usuario al modelo
            model.addAttribute("user", user);
        }

        // Devolver la vista de inicio
        return "home";
    }
}
