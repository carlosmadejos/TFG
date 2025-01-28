package com.example.login_app.controller;

import com.example.login_app.model.User;
import com.example.login_app.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")  // Solo los usuarios con el rol ADMIN pueden acceder a este controlador
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        model.addAttribute("user", user); // Add user to the model
        model.addAttribute("users", userRepository.findAll()); // Add all users for admin management

        return "admin";
    }

    @DeleteMapping("/delete-user/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "Usuario eliminado correctamente";
    }
}
