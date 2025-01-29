package com.example.login_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String showWelcomePage() {
        return "welcome";  // Retorna la plantilla welcome.html
    }
}
