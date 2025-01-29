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
                    "Un plan diseñado para perder grasa progresivamente combinando cardio y entrenamiento de fuerza.",
                    "Principiante",
                    8,
                    "<h3>Semana 1-2: Adaptación y Movilidad</h3>" +
                            "<ul>" +
                            "   <li><b>Lunes:</b> Caminata rápida (30 min) + <b>Entrenamiento de cuerpo completo</b> (3 series de 15 repeticiones)</li>" +
                            "   <ul>" +
                            "       <li>🔹 Sentadillas</li>" +
                            "       <li>🔹 Flexiones de brazos</li>" +
                            "       <li>🔹 Planchas</li>" +
                            "       <li>🔹 Zancadas</li>" +
                            "   </ul>" +
                            "   <li><b>Martes:</b> Cardio moderado (40 min de carrera o bicicleta estática).</li>" +
                            "   <li><b>Miércoles:</b> Descanso activo (yoga, movilidad articular, estiramientos profundos).</li>" +
                            "   <li><b>Jueves:</b> <b>HIIT Básico (20 min)</b></li>" +
                            "   <ul>" +
                            "       <li>🔥 30s sprint + 30s descanso (8 repeticiones)</li>" +
                            "       <li>🔥 Burpees y saltos en caja</li>" +
                            "   </ul>" +
                            "   <li><b>Viernes:</b> Natación o caminata rápida (45 min).</li>" +
                            "   <li><b>Sábado:</b> Cardio de baja intensidad (remo, elíptica, caminata inclinada).</li>" +
                            "   <li><b>Domingo:</b> Descanso.</li>" +
                            "</ul>" +

                            "<h3>Semana 3-4: Aumento de la Intensidad</h3>" +
                            "<ul>" +
                            "   <li>Se introduce el uso de pesas ligeras en circuitos funcionales.</li>" +
                            "   <li>Ejercicios pliométricos como saltos y cambios de dirección.</li>" +
                            "</ul>" +

                            "<h3>Semana 5-6: Combinación de Fuerza y Cardio</h3>" +
                            "<ul>" +
                            "   <li>Ejercicios con resistencia: <b>Pesas o bandas elásticas</b>.</li>" +
                            "   <li>Intervalos de cardio más exigentes: <b>Sprints y escaleras</b>.</li>" +
                            "</ul>" +

                            "<h3>Semana 7-8: Definición y Resistencia</h3>" +
                            "<ul>" +
                            "   <li>Rutinas de resistencia con <b>pesas moderadas</b> y <b>cardio intenso</b>.</li>" +
                            "   <li>Énfasis en la quema de grasa y la tonificación muscular.</li>" +
                            "</ul>"
            ));

            trainingPlanRepository.save(new TrainingPlan(
                    "Ganancia Muscular",
                    "Un programa basado en la hipertrofia, fuerza y definición muscular, con una progresión óptima.",
                    "Intermedio",
                    12,
                    "<h3>Semana 1-4: Volumen (Hipertrofia)</h3>" +
                            "<ul>" +
                            "   <li><b>Lunes:</b> Pecho y tríceps (4 ejercicios, 4x10 reps)</li>" +
                            "   <ul>" +
                            "       <li>💪 Press de banca</li>" +
                            "       <li>💪 Fondos</li>" +
                            "       <li>💪 Aperturas con mancuernas</li>" +
                            "       <li>💪 Extensiones de tríceps</li>" +
                            "   </ul>" +
                            "   <li><b>Martes:</b> Espalda y bíceps (4 ejercicios, 4x10 reps)</li>" +
                            "   <ul>" +
                            "       <li>🏋 Dominadas</li>" +
                            "       <li>🏋 Remo con barra</li>" +
                            "       <li>🏋 Curl de bíceps</li>" +
                            "   </ul>" +
                            "</ul>" +

                            "<h3>Semana 5-8: Fuerza Máxima</h3>" +
                            "<ul>" +
                            "   <li>Aumento del peso levantado en los principales movimientos:</li>" +
                            "   <ul>" +
                            "       <li><b>Sentadilla</b></li>" +
                            "       <li><b>Peso muerto</b></li>" +
                            "       <li><b>Press banca</b></li>" +
                            "       <li><b>Dominadas con lastre</b></li>" +
                            "   </ul>" +
                            "</ul>"
            ));

            trainingPlanRepository.save(new TrainingPlan(
                    "HIIT",
                    "Rutinas de alta intensidad para mejorar la resistencia y la quema de grasa en poco tiempo.",
                    "Avanzado",
                    6,
                    "<h3>Semana 1-2: Introducción a HIIT</h3>" +
                            "<ul>" +
                            "   <li>🔥 <b>Circuito 1:</b> 30s sprint / 30s descanso (8 repeticiones)</li>" +
                            "   <li>🔥 <b>Circuito 2:</b> Burpees, saltos en caja, sentadillas con salto (4 rondas de 40s trabajo/20s descanso)</li>" +
                            "   <li>⏳ <b>Duración:</b> 20-25 min por sesión</li>" +
                            "</ul>" +

                            "<h3>Semana 3-4: Intensidad en Aumento</h3>" +
                            "<ul>" +
                            "   <li>🔥 40s de esfuerzo / 20s de descanso</li>" +
                            "   <li>🔥 Introducción de <b>Battle Ropes, Kettlebell Swings y ejercicios con resistencia</b></li>" +
                            "</ul>"
            ));
        }
    }


}
