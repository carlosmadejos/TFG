package com.example.login_app.service;

import com.example.login_app.model.TrainingPlan;
import com.example.login_app.repository.TrainingPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingPlanService {

    private final TrainingPlanRepository trainingPlanRepository;

    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
    }

    public List<TrainingPlan> getAllTrainingPlans() {
        return trainingPlanRepository.findAll();
    }

    public void addPredefinedPlans() {
        if (trainingPlanRepository.count() == 0) { // Solo añadir si no hay planes en la BD
            trainingPlanRepository.save(new TrainingPlan("Pérdida de Peso", "Cardio y fuerza para perder peso.", "Principiante", 8));
            trainingPlanRepository.save(new TrainingPlan("Ganancia Muscular", "Rutinas de hipertrofia y fuerza.", "Intermedio", 12));
            trainingPlanRepository.save(new TrainingPlan("HIIT", "Alta intensidad para mejorar resistencia.", "Avanzado", 6));
            trainingPlanRepository.save(new TrainingPlan("Movilidad y Flexibilidad", "Yoga y estiramientos.", "Todos los niveles", 4));
        }
    }
}
