package com.example.login_app.controller;

import com.example.login_app.model.TrainingPlan;
import com.example.login_app.service.TrainingPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.annotation.PostConstruct;

@Controller
public class TrainingPlanController {

    private final TrainingPlanService trainingPlanService;

    public TrainingPlanController(TrainingPlanService trainingPlanService) {
        this.trainingPlanService = trainingPlanService;
    }

    @PostConstruct
    public void init() {
        if (trainingPlanService.getAllTrainingPlans().isEmpty()) {
            trainingPlanService.addPredefinedPlans();
        }
    }

    @GetMapping("/training-plans")
    public String showTrainingPlans(Model model) {
        model.addAttribute("trainingPlans", trainingPlanService.getAllTrainingPlans());
        return "training-plans";
    }

    @GetMapping("/training-plans/{id}")
    public String viewTrainingPlanDetails(@PathVariable Long id, Model model) {
        TrainingPlan plan = trainingPlanService.getTrainingPlanById(id);
        if (plan == null) {
            return "redirect:/training-plans"; // Si no existe, redirigir a la lista
        }
        model.addAttribute("plan", plan);
        return "training-plan-details";
    }
}
