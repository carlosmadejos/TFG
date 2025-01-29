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

    public TrainingPlan getTrainingPlanById(Long id) {
        return trainingPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de entrenamiento no encontrado"));
    }

    public void addPredefinedPlans() {
        if (trainingPlanRepository.count() == 0) {
            trainingPlanRepository.save(new TrainingPlan(
                    "Pérdida de Peso",
                    "Cardio y fuerza para perder peso.",
                    "Principiante",
                    8,
                    "Semana 1-2:\n" +
                            "- Lunes: 30 min de caminata rápida + entrenamiento de cuerpo completo (peso corporal)\n" +
                            "- Martes: 40 min de cardio moderado (correr o bicicleta estática)\n" +
                            "- Miércoles: Descanso activo (yoga o estiramientos)\n" +
                            "- Jueves: Entrenamiento HIIT de 20 min + pesas ligeras\n" +
                            "- Viernes: 45 min de caminata rápida o natación\n" +
                            "- Sábado: Cardio de baja intensidad\n" +
                            "- Domingo: Descanso\n\n" +
                            "Semana 3-4:\n" +
                            "- Aumento de la intensidad en cardio y resistencia\n" +
                            "- Incorporación de circuitos funcionales con pesas ligeras\n\n" +
                            "Semana 5-6:\n" +
                            "- Combinación de fuerza y HIIT\n" +
                            "- Reducción del tiempo de descanso entre ejercicios\n\n" +
                            "Semana 7-8:\n" +
                            "- Entrenamiento más estructurado con énfasis en quema de grasa\n" +
                            "- Rutinas de resistencia con pesas moderadas y cardio intenso."
            ));

            trainingPlanRepository.save(new TrainingPlan(
                    "Ganancia Muscular",
                    "Rutinas de hipertrofia y fuerza para maximizar el crecimiento muscular.",
                    "Intermedio",
                    12,
                    "Semana 1-4: Fase de Volumen\n" +
                            "- Lunes: Pecho y tríceps (4 ejercicios, 4x10 reps)\n" +
                            "- Martes: Espalda y bíceps (4 ejercicios, 4x10 reps)\n" +
                            "- Miércoles: Piernas y glúteos (5 ejercicios, 4x12 reps)\n" +
                            "- Jueves: Hombros y abdomen (4 ejercicios, 4x10 reps)\n" +
                            "- Viernes: Full body funcional\n" +
                            "- Sábado y domingo: Descanso o cardio ligero\n\n" +
                            "Semana 5-8: Fase de Fuerza\n" +
                            "- Aumento progresivo del peso levantado\n" +
                            "- Descanso mayor entre series (90-120 segundos)\n" +
                            "- Rutinas con énfasis en levantamientos compuestos (sentadilla, press banca, peso muerto)\n\n" +
                            "Semana 9-12: Fase de Definición\n" +
                            "- Mayor cantidad de repeticiones con pesos moderados\n" +
                            "- Incorporación de superseries y dropsets\n" +
                            "- Cardio post-entrenamiento para mejorar definición muscular."
            ));

            trainingPlanRepository.save(new TrainingPlan(
                    "HIIT",
                    "Rutinas de alta intensidad para mejorar resistencia y quemar grasa en poco tiempo.",
                    "Avanzado",
                    6,
                    "Semana 1-2: Introducción a HIIT\n" +
                            "- 30 segundos de sprint / 30 segundos de descanso (x8 repeticiones)\n" +
                            "- Ejercicios funcionales como burpees y saltos en caja\n" +
                            "- Sesiones de 20-25 min\n\n" +
                            "Semana 3-4: Incremento de Intensidad\n" +
                            "- 40 segundos de esfuerzo / 20 segundos de descanso\n" +
                            "- Combinación de ejercicios con pesas ligeras\n" +
                            "- Circuitos de cuerpo completo con movimientos explosivos\n\n" +
                            "Semana 5-6: HIIT Extremo\n" +
                            "- Rutinas de 30 min con descanso mínimo\n" +
                            "- Incorporación de ejercicios con bandas de resistencia\n" +
                            "- Aumento de la complejidad en los movimientos (ej: saltos pliométricos y sprints combinados con kettlebells)."
            ));
        }
    }
}
