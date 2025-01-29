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
                    "Un plan diseñado para perder grasa progresivamente combinando cardio y entrenamiento de fuerza. Se enfoca en la adaptación inicial, el incremento de intensidad y la combinación de fuerza y resistencia.",
                    "Principiante",
                    8,
                    "<h3>Semana 1-2: Adaptación y Movilidad</h3>" +
                            "<p>Durante las primeras dos semanas, el objetivo es mejorar la movilidad, adaptarse a la actividad física y fortalecer la resistencia aeróbica.</p>" +
                            "<ul>" +
                            "   <li><b>Lunes:</b> Caminata rápida (30 min) + <b>Entrenamiento de cuerpo completo</b> (3 series de 15 repeticiones)</li>" +
                            "   <ul>" +
                            "       <li>🔹 <b>Sentadillas:</b> De pie con los pies al ancho de los hombros, flexiona las rodillas y baja las caderas manteniendo la espalda recta. Empuja con los talones para regresar a la posición inicial.</li>" +
                            "       <li>🔹 <b>Flexiones de brazos:</b> Mantén las manos alineadas con los hombros, baja el cuerpo doblando los codos hasta que el pecho casi toque el suelo y luego empuja hacia arriba.</li>" +
                            "       <li>🔹 <b>Planchas:</b> Apoya los antebrazos en el suelo y mantén el cuerpo recto desde la cabeza hasta los talones. Aprieta el abdomen y sostén la posición.</li>" +
                            "       <li>🔹 <b>Zancadas:</b> Da un paso hacia adelante con una pierna y baja las caderas hasta que ambas rodillas formen un ángulo de 90°. Vuelve a la posición inicial y cambia de pierna.</li>" +
                            "   </ul>" +
                            "   <li><b>Martes:</b> Cardio moderado (40 min de carrera a ritmo bajo o bicicleta estática).</li>" +
                            "   <li><b>Miércoles:</b> Descanso activo (yoga, movilidad articular y estiramientos profundos para evitar lesiones).</li>" +
                            "   <li><b>Jueves:</b> <b>HIIT Básico (20 min)</b></li>" +
                            "   <ul>" +
                            "       <li>🔥 <b>Sprints:</b> Corre a máxima velocidad durante 30 segundos y descansa 30 segundos. Repite 8 veces.</li>" +
                            "       <li>🔥 <b>Burpees:</b> Desde posición de pie, agáchate y coloca las manos en el suelo, salta con los pies hacia atrás para una plancha, realiza una flexión, salta de nuevo hacia adelante y luego salta verticalmente con los brazos extendidos.</li>" +
                            "       <li>🔥 <b>Saltos en caja:</b> Salta sobre una superficie elevada (como un banco bajo o una caja de 40 cm). Baja controladamente y repite.</li>" +
                            "   </ul>" +
                            "   <li><b>Viernes:</b> Natación o caminata rápida (45 min).</li>" +
                            "   <li><b>Sábado:</b> Cardio de baja intensidad (remo, elíptica, caminata inclinada a baja velocidad).</li>" +
                            "   <li><b>Domingo:</b> Descanso.</li>" +
                            "</ul>" +

                            "<h3>Semana 3-4: Aumento de la Intensidad</h3>" +
                            "<p>A partir de la tercera semana, se aumentará la dificultad con ejercicios más demandantes para mejorar la resistencia y la fuerza muscular.</p>" +
                            "<ul>" +
                            "   <li>✅ Introducción al uso de pesas ligeras en circuitos funcionales.</li>" +
                            "   <li>✅ Incorporación de ejercicios pliométricos para mejorar la potencia y la agilidad:</li>" +
                            "   <ul>" +
                            "       <li>🔥 <b>Saltos en cuclillas:</b> Baja en sentadilla y salta explosivamente hacia arriba, aterrizando suavemente.</li>" +
                            "       <li>🔥 <b>Cambios de dirección:</b> Corre 5 metros en una dirección y cambia rápidamente de sentido.</li>" +
                            "   </ul>" +
                            "</ul>" +

                            "<h3>Semana 5-6: Combinación de Fuerza y Cardio</h3>" +
                            "<p>Se incorpora mayor intensidad en los ejercicios de fuerza y se introducen entrenamientos interválicos de alta exigencia.</p>" +
                            "<ul>" +
                            "   <li>💪 <b>Ejercicios con resistencia:</b> Se introducen pesas o bandas elásticas para mejorar la fuerza.</li>" +
                            "   <li>🏃 <b>Cardio de alta intensidad:</b> Sprints y escaleras, mejorando la capacidad cardiovascular.</li>" +
                            "   <li>💥 <b>Burpees avanzados:</b> Se agrega un salto más alto y una flexión adicional.</li>" +
                            "   <li>🔥 <b>Plancha con toques de hombro:</b> Mantén una plancha y toca un hombro con la mano contraria sin mover la cadera.</li>" +
                            "</ul>" +

                            "<h3>Semana 7-8: Definición y Resistencia</h3>" +
                            "<p>El entrenamiento final enfatiza la resistencia muscular y la quema de grasa a través de sesiones combinadas de cardio y fuerza.</p>" +
                            "<ul>" +
                            "   <li>🔥 <b>Rutinas de resistencia con pesas moderadas:</b> Mayor número de repeticiones con menos descanso.</li>" +
                            "   <li>💪 <b>Entrenamiento de circuito:</b> Se combinan ejercicios de fuerza con series cortas de cardio para mantener el ritmo cardíaco elevado.</li>" +
                            "   <li>💨 <b>Cardio HIIT final:</b> Intervalos de 40 segundos de esfuerzo / 20 segundos de descanso con burpees, sprints y saltos en caja.</li>" +
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
