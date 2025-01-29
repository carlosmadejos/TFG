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
                    "Este plan está diseñado para reducir grasa corporal mediante una combinación efectiva de cardio y entrenamiento de fuerza. Inicia con una fase de adaptación para preparar al cuerpo y luego progresa hacia entrenamientos de mayor intensidad, combinando ejercicios funcionales, pliométricos e HIIT.",
                    "Principiante",
                    8,

                    "<h3>Semana 1-2: Adaptación y Movilidad</h3>" +
                            "<p>El objetivo en estas semanas es mejorar la movilidad, fortalecer la base muscular y acostumbrar al cuerpo a la actividad física sin generar fatiga excesiva.</p>" +

                            "<ul>" +
                            "   <li><b>Lunes:</b> Caminata rápida (30 min) + <b>Entrenamiento de cuerpo completo</b> (3 series de 15 repeticiones)</li>" +
                            "   <ul>" +
                            "       <li>🔹 <b>Sentadillas:</b> Baja lentamente hasta que los muslos estén paralelos al suelo. Mantén la espalda recta y las rodillas alineadas con los pies. Empuja con los talones para volver a la posición inicial.</li>" +
                            "       <li>🔹 <b>Flexiones de brazos:</b> Controla el descenso hasta que el pecho casi toque el suelo. Si es difícil, apoya las rodillas.</li>" +
                            "       <li>🔹 <b>Planchas:</b> Activa el abdomen y los glúteos. Evita que las caderas se hundan o suban demasiado.</li>" +
                            "       <li>🔹 <b>Zancadas:</b> Mantén el torso erguido, baja controladamente y empuja con la pierna adelantada para volver a la posición inicial.</li>" +
                            "   </ul>" +
                            "   <li><b>Martes:</b> Cardio moderado: 40 min de caminata rápida o bicicleta estática con resistencia baja.</li>" +
                            "   <li><b>Miércoles:</b> Descanso activo: Yoga, movilidad articular, estiramientos dinámicos.</li>" +
                            "   <li><b>Jueves:</b> <b>HIIT Básico (20 min)</b></li>" +
                            "   <ul>" +
                            "       <li>🔥 <b>Sprints:</b> Corre a máxima velocidad por 30 segundos, seguido de 30 segundos de descanso. Repite 8 veces.</li>" +
                            "       <li>🔥 <b>Burpees:</b> Si es difícil, omite la flexión o el salto.</li>" +
                            "       <li>🔥 <b>Saltos en caja:</b> Aterriza con las rodillas ligeramente flexionadas para evitar impacto en las articulaciones.</li>" +
                            "   </ul>" +
                            "   <li><b>Viernes:</b> Natación o caminata rápida (45 min).</li>" +
                            "   <li><b>Sábado:</b> Cardio de baja intensidad (remo, elíptica, caminata inclinada a ritmo moderado).</li>" +
                            "   <li><b>Domingo:</b> Descanso.</li>" +
                            "</ul>" +

                            "<h3>Semana 3-4: Aumento de la Intensidad</h3>" +
                            "<p>Se incrementa la dificultad incorporando peso adicional, trabajo de resistencia y ejercicios de agilidad.</p>" +
                            "<ul>" +
                            "   <li>✅ Se introduce el uso de pesas ligeras en circuitos funcionales.</li>" +
                            "   <li>✅ Se incorporan ejercicios pliométricos para mejorar potencia y agilidad:</li>" +
                            "   <ul>" +
                            "       <li>🔥 <b>Saltos en cuclillas:</b> Realiza una sentadilla profunda y salta explosivamente. Aterriza con control.</li>" +
                            "       <li>🔥 <b>Cambios de dirección:</b> Corre 5 metros en una dirección y cambia rápidamente de sentido.</li>" +
                            "   </ul>" +
                            "</ul>" +

                            "<h3>Semana 5-6: Combinación de Fuerza y Cardio</h3>" +
                            "<p>El entrenamiento comienza a enfocarse en la quema calórica con circuitos de alta intensidad.</p>" +
                            "<ul>" +
                            "   <li>💪 <b>Ejercicios con resistencia:</b> Se introducen pesas o bandas elásticas para aumentar la intensidad.</li>" +
                            "   <li>🏃 <b>Cardio de alta intensidad:</b> Sprints cortos y ejercicios de escalera de agilidad.</li>" +
                            "   <li>💥 <b>Burpees avanzados:</b> Añade una sentadilla extra antes del salto.</li>" +
                            "   <li>🔥 <b>Plancha con toques de hombro:</b> Mantén el core firme y toca alternativamente los hombros sin mover las caderas.</li>" +
                            "</ul>" +

                            "<h3>Semana 7-8: Definición y Resistencia</h3>" +
                            "<p>Fase final del plan, centrada en tonificación y pérdida de grasa.</p>" +
                            "<ul>" +
                            "   <li>🔥 <b>Rutinas de resistencia con pesas moderadas:</b> Menos descanso entre series.</li>" +
                            "   <li>💪 <b>Entrenamiento de circuito:</b> Se combinan ejercicios de fuerza y cardio para mantener un alto gasto calórico.</li>" +
                            "   <li>💨 <b>Cardio HIIT final:</b> 40 segundos de esfuerzo / 20 segundos de descanso con burpees, sprints y saltos en caja.</li>" +
                            "</ul>" +

                            "<h3>Estiramientos y Movilidad</h3>" +
                            "<p>Los estiramientos son fundamentales para evitar lesiones y mejorar el rendimiento. Realiza cada uno durante 20-30 segundos.</p>" +
                            "<ul>" +
                            "   <li>🧘 <b>Estiramiento de cuádriceps:</b> De pie, lleva un pie hacia el glúteo sujetándolo con la mano. Mantén la espalda recta.</li>" +
                            "   <li>🧘 <b>Estiramiento de isquiotibiales:</b> Desde posición de pie, flexiona el torso hacia adelante intentando tocar los dedos de los pies.</li>" +
                            "   <li>🧘 <b>Estiramiento de espalda y hombros:</b> Extiende un brazo sobre el pecho y presiona suavemente con la otra mano.</li>" +
                            "   <li>🧘 <b>Rotación de cadera:</b> De pie, haz círculos amplios con las caderas en ambas direcciones.</li>" +
                            "</ul>" +

                            "<h3>Nutrición para la Pérdida de Peso</h3>" +
                            "<p>Una alimentación adecuada potenciará la quema de grasa y mejorará tu rendimiento.</p>" +
                            "<ul>" +
                            "   <li>🥩 <b>Prioriza proteínas:</b> Pollo, pescado, huevos y legumbres ayudan a preservar la masa muscular.</li>" +
                            "   <li>🥦 <b>Consume suficientes vegetales:</b> Aportan fibra y micronutrientes esenciales.</li>" +
                            "   <li>🥑 <b>Grasas saludables:</b> Aguacate, frutos secos y aceite de oliva son esenciales para la recuperación.</li>" +
                            "   <li>💧 <b>Hidratación constante:</b> Bebe al menos 2L de agua al día para optimizar el metabolismo.</li>" +
                            "</ul>" +

                            "<h3>Recuperación Muscular</h3>" +
                            "<p>Descansar correctamente es tan importante como entrenar.</p>" +
                            "<ul>" +
                            "   <li>🛌 <b>7-9 horas de sueño:</b> El descanso adecuado favorece la recuperación y el crecimiento muscular.</li>" +
                            "   <li>🧊 <b>Baños de agua fría:</b> Reducen la inflamación y alivian el dolor muscular.</li>" +
                            "   <li>💆 <b>Masajes y foam rolling:</b> Mejora la circulación y relaja la musculatura.</li>" +
                            "   <li>🍽️ <b>Comida post-entrenamiento:</b> Consume proteína y carbohidratos para acelerar la recuperación.</li>" +
                            "</ul>"
            ));


            trainingPlanRepository.save(new TrainingPlan(
                    "Ganancia Muscular",
                    "Un programa basado en la hipertrofia, fuerza y definición muscular, con una progresión óptima. Se divide en tres fases clave: volumen para aumentar masa muscular, fuerza para desarrollar potencia y definición para mejorar la calidad muscular.",
                    "Intermedio",
                    12,

                    "<h3>Semana 1-4: Volumen (Hipertrofia)</h3>" +
                            "<p>El objetivo en esta fase es maximizar el crecimiento muscular mediante una combinación de ejercicios multiarticulares y aislamiento. Se recomienda una alimentación alta en proteínas y carbohidratos para potenciar la recuperación.</p>" +

                            "<ul>" +
                            "   <li><b>Lunes: Pecho y Tríceps</b> (4 ejercicios, 4x10 reps)</li>" +
                            "   <ul>" +
                            "       <li>💪 <b>Press de banca con barra:</b> Fundamental para el desarrollo del pectoral. Mantén los pies firmes en el suelo, baja la barra hasta el pecho y empuja explosivamente.</li>" +
                            "       <li>💪 <b>Fondos en paralelas:</b> Trabaja el pecho y los tríceps. Baja controladamente hasta que los codos formen un ángulo de 90° y empuja hacia arriba.</li>" +
                            "       <li>💪 <b>Aperturas con mancuernas:</b> Ideal para ensanchar el pecho. Mantén los codos ligeramente flexionados y baja las mancuernas en un arco controlado.</li>" +
                            "       <li>💪 <b>Extensiones de tríceps con mancuerna:</b> Sujeta una mancuerna con ambas manos y baja detrás de la cabeza, manteniendo los codos fijos.</li>" +
                            "   </ul>" +

                            "   <li><b>Martes: Espalda y Bíceps</b> (4 ejercicios, 4x10 reps)</li>" +
                            "   <ul>" +
                            "       <li>🏋 <b>Dominadas pronas:</b> Un ejercicio esencial para la espalda. Usa un agarre ancho, sube hasta que la barbilla supere la barra y baja lentamente.</li>" +
                            "       <li>🏋 <b>Remo con barra:</b> Mantén la espalda recta, lleva la barra hacia el abdomen y controla el movimiento de bajada.</li>" +
                            "       <li>🏋 <b>Curl de bíceps con barra:</b> Mantén los codos fijos, sube la barra sin balanceo y aprieta los bíceps al final del movimiento.</li>" +
                            "       <li>🏋 <b>Face pulls con cuerda:</b> Ayuda a desarrollar los músculos del trapecio y mejorar la postura.</li>" +
                            "   </ul>" +

                            "   <li><b>Miércoles: Piernas y Glúteos</b> (5 ejercicios, 4x12 reps)</li>" +
                            "   <ul>" +
                            "       <li>🏋 <b>Sentadilla profunda con barra:</b> Baja hasta que las caderas estén por debajo de las rodillas, manteniendo la espalda recta.</li>" +
                            "       <li>🏋 <b>Peso muerto rumano:</b> Activa los isquiotibiales y glúteos. Mantén la barra cerca del cuerpo y baja lentamente.</li>" +
                            "       <li>🏋 <b>Hip thrust con barra:</b> Fundamental para la activación de glúteos. Apoya la espalda en un banco y empuja con la cadera hacia arriba.</li>" +
                            "       <li>🏋 <b>Estocadas con mancuernas:</b> Controla el equilibrio y baja hasta que la rodilla trasera casi toque el suelo.</li>" +
                            "       <li>🏋 <b>Elevación de talones para gemelos:</b> Realiza el movimiento con rango completo y sostén la contracción máxima por 2 segundos.</li>" +
                            "   </ul>" +

                            "   <li><b>Jueves: Hombros y Abdomen</b> (4 ejercicios, 4x10 reps)</li>" +
                            "   <ul>" +
                            "       <li>🏋 <b>Press militar con barra:</b> Empuja la barra por encima de la cabeza sin arquear la espalda.</li>" +
                            "       <li>🏋 <b>Elevaciones laterales con mancuernas:</b> Levanta las mancuernas hasta la altura de los hombros, sin balanceo.</li>" +
                            "       <li>🏋 <b>Planchas con peso:</b> Mantén la posición con un disco en la espalda para mayor intensidad.</li>" +
                            "       <li>🏋 <b>Crunch en polea con cuerda:</b> Flexiona el torso hacia abajo manteniendo la tensión en los abdominales.</li>" +
                            "   </ul>" +

                            "   <li><b>Viernes: Full Body Funcional</b></li>" +
                            "   <ul>" +
                            "       <li>🔥 Circuito de alta intensidad con movimientos multiarticulares.</li>" +
                            "   </ul>" +
                            "</ul>" +

                            "<h3>Semana 5-8: Fuerza Máxima</h3>" +
                            "<p>Se reduce el número de repeticiones y se enfoca en aumentar el peso levantado en los ejercicios principales.</p>" +

                            "<ul>" +
                            "   <li>💪 <b>Ejercicios clave:</b></li>" +
                            "   <ul>" +
                            "       <li>🏋 <b>Sentadilla con barra:</b> 5 series de 5 reps con aumento progresivo de carga.</li>" +
                            "       <li>🏋 <b>Peso muerto convencional:</b> Mayor control en la fase excéntrica para mejorar la activación muscular.</li>" +
                            "       <li>🏋 <b>Press banca pesado:</b> Técnica estricta con enfoque en fuerza explosiva.</li>" +
                            "       <li>🏋 <b>Dominadas con lastre:</b> Añadir peso extra para aumentar la dificultad.</li>" +
                            "   </ul>" +
                            "</ul>" +

                            "<h3>Semana 9-12: Definición y Resistencia</h3>" +
                            "<p>Esta fase se centra en mantener la fuerza mientras se aumenta la quema de grasa con superseries y dropsets.</p>" +

                            "<ul>" +
                            "   <li>🔥 <b>Ejercicios de superseries:</b> Combina dos ejercicios sin descanso para maximizar el estímulo muscular.</li>" +
                            "   <li>🔥 <b>Uso de pesos moderados:</b> 15-20 repeticiones por serie para aumentar la resistencia muscular.</li>" +
                            "   <li>🔥 <b>Cardio post-entrenamiento:</b> 20-30 min de cardio moderado para ayudar a la definición.</li>" +
                            "</ul>"
            ));


            trainingPlanRepository.save(new TrainingPlan(
                    "HIIT",
                    "Rutinas de alta intensidad diseñadas para maximizar la quema de grasa y mejorar la resistencia cardiovascular en poco tiempo. Este programa combina intervalos de alta exigencia con descansos estratégicos para mejorar el rendimiento.",
                    "Avanzado",
                    6,

                    "<h3>Semana 1-2: Introducción a HIIT</h3>" +
                            "<p>El objetivo es adaptar el cuerpo a ejercicios de alta intensidad, mejorar la resistencia cardiovascular y acostumbrar las articulaciones al impacto.</p>" +
                            "<ul>" +
                            "   <li>🔥 <b>Circuito 1: Sprints</b> (30s esfuerzo / 30s descanso x 8 repeticiones)</li>" +
                            "   <ul>" +
                            "       <li>🏃 <b>Ejecutando correctamente:</b> Corre a máxima velocidad durante 30 segundos, manteniendo el torso erguido y las rodillas elevadas. Descansa caminando o trotando lentamente.</li>" +
                            "       <li>🎯 <b>Beneficio:</b> Mejora explosividad, quema grasa y desarrolla resistencia anaeróbica.</li>" +
                            "   </ul>" +

                            "   <li>🔥 <b>Circuito 2: Ejercicios de cuerpo completo</b> (4 rondas de 40s trabajo / 20s descanso)</li>" +
                            "   <ul>" +
                            "       <li>💥 <b>Burpees:</b> Desde posición de pie, baja a una sentadilla, coloca las manos en el suelo, estira las piernas para quedar en posición de plancha, realiza una flexión y salta al volver arriba.</li>" +
                            "       <li>📦 <b>Saltos en caja:</b> Usa una plataforma estable, flexiona las rodillas y salta sobre la caja aterrizando con control.</li>" +
                            "       <li>🏋 <b>Sentadillas con salto:</b> Realiza una sentadilla profunda y al subir, salta explosivamente extendiendo las piernas.</li>" +
                            "   </ul>" +

                            "   <li>⏳ <b>Duración:</b> 20-25 min por sesión.</li>" +
                            "</ul>" +

                            "<h3>Semana 3-4: Intensidad en Aumento</h3>" +
                            "<p>Se aumenta la duración del esfuerzo y se incorporan nuevos ejercicios con resistencia.</p>" +
                            "<ul>" +
                            "   <li>🔥 40s de esfuerzo / 20s de descanso.</li>" +
                            "   <li>🔥 Introducción de ejercicios con equipamiento funcional.</li>" +
                            "</ul>" +

                            "<h3>Ejercicios Avanzados:</h3>" +
                            "<ul>" +
                            "   <li>🔗 <b>Battle Ropes:</b> Ondea las cuerdas con movimientos explosivos alternando los brazos. Mantén el core activo.</li>" +
                            "   <li>🔄 <b>Kettlebell Swings:</b> Agarra la kettlebell con ambas manos, flexiona ligeramente las rodillas y usa la cadera para impulsarla hasta la altura del pecho.</li>" +
                            "   <li>🏋 <b>Sentadilla Goblet:</b> Sostén una kettlebell o mancuerna en el pecho y realiza sentadillas profundas.</li>" +
                            "   <li>💥 <b>Saltos Pliométricos:</b> Realiza zancadas explosivas alternando las piernas en el aire.</li>" +
                            "</ul>" +

                            "<h3>Semana 5-6: HIIT Extremo</h3>" +
                            "<p>El nivel más desafiante del programa. Se reducen los tiempos de descanso y se incorporan ejercicios combinados.</p>" +
                            "<ul>" +
                            "   <li>🔥 <b>Entrenamiento Tabata:</b> 20s trabajo / 10s descanso x 8 rondas por ejercicio.</li>" +
                            "   <li>🔥 Uso de bandas de resistencia para mayor intensidad.</li>" +
                            "   <li>🔥 Combinación de movimientos pliométricos con cardio (ej: sprints + saltos en caja).</li>" +
                            "</ul>"
            ));

        }
    }


}
