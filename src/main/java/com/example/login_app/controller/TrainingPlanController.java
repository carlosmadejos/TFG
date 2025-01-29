package com.example.login_app.controller;

import com.example.login_app.model.TrainingPlan;
import com.example.login_app.service.TrainingPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TrainingPlanController {

    private final TrainingPlanService trainingPlanService;

    public TrainingPlanController(TrainingPlanService trainingPlanService) {
        this.trainingPlanService = trainingPlanService;
    }

    @GetMapping("/training-plans")
    public String showTrainingPlans(Model model) {
        trainingPlanService.addPredefinedPlans();
        List<TrainingPlan> trainingPlans = trainingPlanService.getAllTrainingPlans();
        model.addAttribute("trainingPlans", trainingPlans);
        return "training-plans";
    }
}
