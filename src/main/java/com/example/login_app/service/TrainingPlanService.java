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
                            "<p>Los estiramientos ayudan a prevenir lesiones, mejorar la flexibilidad y optimizar el rendimiento. Se recomienda realizarlos después del entrenamiento para relajar los músculos y mejorar la recuperación.</p>" +

                            "<h4>🔹 Estiramientos para el Tren Superior</h4>" +
                            "<ul>" +
                            "   <li>🧘 <b>Estiramiento de pectorales:</b> Apoya la mano en una pared con el codo a 90° y gira lentamente el torso en la dirección opuesta hasta sentir la tensión en el pecho. Mantén 20-30 segundos por lado.</li>" +
                            "   <li>🧘 <b>Estiramiento de tríceps:</b> Lleva un brazo por detrás de la cabeza y presiona el codo con la mano opuesta.</li>" +
                            "   <li>🧘 <b>Estiramiento de espalda alta:</b> Agarra un poste o una barra y flexiona el torso hacia atrás, sintiendo la extensión en la parte superior de la espalda.</li>" +
                            "   <li>🧘 <b>Estiramiento de bíceps:</b> Extiende el brazo hacia atrás con la palma hacia afuera apoyándola en una pared.</li>" +
                            "</ul>" +

                            "<h4>🔹 Estiramientos para el Tren Inferior</h4>" +
                            "<ul>" +
                            "   <li>🦵 <b>Estiramiento de cuádriceps:</b> De pie, lleva un pie hacia el glúteo sujetándolo con la mano. Mantén la espalda recta y presiona suavemente el talón contra el glúteo.</li>" +
                            "   <li>🦵 <b>Estiramiento de isquiotibiales:</b> Sentado con una pierna extendida, inclina el torso hacia adelante manteniendo la espalda recta.</li>" +
                            "   <li>🦵 <b>Estiramiento de glúteos:</b> Cruza una pierna sobre la otra y lleva la rodilla hacia el pecho.</li>" +
                            "   <li>🦵 <b>Estiramiento de gemelos:</b> Apoya las manos contra una pared y empuja el talón hacia atrás sin levantarlo del suelo.</li>" +
                            "</ul>" +

                            "<h4>🔹 Movilidad Articular</h4>" +
                            "<ul>" +
                            "   <li>🔄 <b>Círculos de hombros:</b> Gira los hombros hacia adelante y hacia atrás en movimientos controlados.</li>" +
                            "   <li>🔄 <b>Movilidad de cadera:</b> Realiza círculos con la pierna para mejorar la movilidad.</li>" +
                            "   <li>🔄 <b>Rotaciones de cuello:</b> Gira la cabeza lentamente en círculos pequeños para liberar tensión.</li>" +
                            "</ul>"+

                            "<h3>Nutrición para la Pérdida de Peso</h3>" +
                            "<p>Una alimentación adecuada potenciará la quema de grasa y mejorará tu rendimiento.</p>" +
                            "<ul>" +
                            "   <li>🥩 <b>Prioriza proteínas:</b> Pollo, pescado, huevos y legumbres ayudan a preservar la masa muscular.</li>" +
                            "   <li>🥦 <b>Consume suficientes vegetales:</b> Aportan fibra y micronutrientes esenciales.</li>" +
                            "   <li>🥑 <b>Grasas saludables:</b> Aguacate, frutos secos y aceite de oliva son esenciales para la recuperación.</li>" +
                            "   <li>💧 <b>Hidratación constante:</b> Bebe al menos 2L de agua al día para optimizar el metabolismo.</li>" +
                            "</ul>" +

                            "<h3>Recuperación y Optimización</h3>" +
                            "<p>El descanso y la recuperación son igual de importantes que el entrenamiento. Sin una adecuada recuperación, el crecimiento muscular se verá afectado.</p>" +

                            "<h4>🔹 Sueño y Descanso</h4>" +
                            "<ul>" +
                            "   <li>🛌 <b>Duración recomendada:</b> 7-9 horas por noche.</li>" +
                            "   <li>🌙 <b>Evita pantallas antes de dormir:</b> La luz azul afecta la producción de melatonina.</li>" +
                            "</ul>" +

                            "<h4>🔹 Técnicas de Recuperación</h4>" +
                            "<ul>" +
                            "   <li>🧊 <b>Baños de contraste:</b> Alternar agua fría y caliente mejora la circulación y reduce la inflamación.</li>" +
                            "   <li>💆 <b>Masajes con foam roller:</b> Ayudan a eliminar nudos musculares y mejorar la elasticidad.</li>" +
                            "   <li>🧘 <b>Yoga y estiramientos dinámicos:</b> Ideales para mejorar la movilidad y reducir la rigidez muscular.</li>" +
                            "</ul>" +

                            "<h4>🔹 Comida Post-Entrenamiento</h4>" +
                            "<ul>" +
                            "   <li>🍗 <b>Proteína + Carbohidratos:</b> Ayuda a reparar las fibras musculares y reponer glucógeno.</li>" +
                            "   <li>🍌 <b>Ejemplo de comida post-entrenamiento:</b> Pechuga de pollo con arroz integral y verduras.</li>" +
                            "</ul>" +

                            "<h4>🔹 Estrategias para Evitar el Sobreentrenamiento</h4>" +
                            "<ul>" +
                            "   <li>🛑 <b>Escucha a tu cuerpo:</b> Si sientes fatiga extrema, descansa.</li>" +
                            "   <li>📆 <b>Incluye días de descanso activo:</b> Caminatas suaves o natación ligera.</li>" +
                            "</ul>"
            ));


            trainingPlanRepository.save(new TrainingPlan(
                    "Ganancia Muscular",
                    "Un programa progresivo dividido en tres fases clave: volumen para construir masa muscular, fuerza para desarrollar potencia y definición para optimizar la composición corporal. Se enfoca en ejercicios compuestos, técnicas avanzadas y una planificación estructurada.",
                    "Intermedio",
                    12,

                    "<h3>Semana 1-4: Volumen (Hipertrofia)</h3>" +
                            "<p>Durante esta fase, se trabaja en el crecimiento muscular aumentando la carga progresivamente y maximizando el tiempo bajo tensión. Se recomienda una dieta alta en proteínas y carbohidratos para favorecer la recuperación.</p>" +

                            "<ul>" +
                            "   <li><b>Lunes: Pecho y Tríceps</b> (5 ejercicios, 4x10-12 reps)</li>" +
                            "   <ul>" +
                            "       <li>💪 <b>Press de banca con barra:</b> Coloca los pies firmes en el suelo, baja la barra hasta tocar el pecho y empuja controladamente. Mantén los codos a 45° para proteger los hombros.</li>" +
                            "       <li>💪 <b>Press inclinado con mancuernas:</b> Activa la parte superior del pectoral. Baja las mancuernas hasta que los codos formen un ángulo de 90°.</li>" +
                            "       <li>💪 <b>Fondos en paralelas:</b> Baja controladamente y sube sin bloquear los codos.</li>" +
                            "       <li>💪 <b>Aperturas con mancuernas:</b> Mantén una ligera flexión en los codos y evita rebotar en la fase final.</li>" +
                            "       <li>💪 <b>Extensiones de tríceps en polea:</b> Mantén los codos fijos y aprieta en la fase final.</li>" +
                            "   </ul>" +

                            "   <li><b>Martes: Espalda y Bíceps</b> (5 ejercicios, 4x10-12 reps)</li>" +
                            "   <ul>" +
                            "       <li>🏋 <b>Dominadas con agarre ancho:</b> Ideal para desarrollar el dorsal ancho. Sube hasta que la barbilla pase la barra.</li>" +
                            "       <li>🏋 <b>Remo con barra:</b> Mantén la espalda recta y lleva la barra hacia el abdomen.</li>" +
                            "       <li>🏋 <b>Pull-over con mancuerna:</b> Expande la caja torácica y mejora la conexión mente-músculo.</li>" +
                            "       <li>🏋 <b>Curl de bíceps con barra Z:</b> Agarre cerrado para mayor activación.</li>" +
                            "       <li>🏋 <b>Martillo con mancuernas:</b> Trabaja el braquiorradial y antebrazos.</li>" +
                            "   </ul>" +

                            "   <li><b>Miércoles: Piernas y Glúteos</b> (5 ejercicios, 4x12-15 reps)</li>" +
                            "   <ul>" +
                            "       <li>🏋 <b>Sentadilla profunda:</b> Baja hasta que los muslos pasen la línea de las rodillas.</li>" +
                            "       <li>🏋 <b>Peso muerto rumano:</b> Mantén la barra pegada a las piernas y activa los isquiotibiales.</li>" +
                            "       <li>🏋 <b>Hip thrust con barra:</b> Contrae los glúteos al final del movimiento.</li>" +
                            "       <li>🏋 <b>Estocadas con mancuernas:</b> Alterna cada pierna con control.</li>" +
                            "       <li>🏋 <b>Elevación de talones para gemelos:</b> Usa un rango completo de movimiento.</li>" +
                            "   </ul>" +

                            "   <li><b>Jueves: Hombros y Abdomen</b> (5 ejercicios, 4x10-12 reps)</li>" +
                            "   <ul>" +
                            "       <li>🏋 <b>Press militar con barra:</b> Mantén el core firme y empuja la barra por encima de la cabeza.</li>" +
                            "       <li>🏋 <b>Elevaciones laterales con mancuernas:</b> Levanta hasta la altura de los hombros sin balanceo.</li>" +
                            "       <li>🏋 <b>Remo al mentón con barra Z:</b> Trabaja deltoides y trapecios.</li>" +
                            "       <li>🏋 <b>Crunch en polea con cuerda:</b> Máxima tensión en los abdominales.</li>" +
                            "       <li>🏋 <b>Planchas con peso:</b> Mantén el core firme.</li>" +
                            "   </ul>" +
                            "</ul>" +

                            "<h3>Semana 5-8: Fuerza Máxima</h3>" +
                            "<p>Reducción de repeticiones y aumento del peso en ejercicios clave.</p>" +
                            "<ul>" +
                            "   <li>💪 <b>Ejercicios de carga progresiva:</b> Sentadilla, peso muerto, press banca y dominadas con lastre.</li>" +
                            "   <li>🔥 <b>Descanso extendido:</b> 90-120 segundos entre series.</li>" +
                            "</ul>" +

                            "<h3>Semana 9-12: Definición Muscular</h3>" +
                            "<p>Mayor énfasis en resistencia muscular y reducción de grasa.</p>" +
                            "<ul>" +
                            "   <li>🔥 <b>Repeticiones más altas:</b> 12-15 repeticiones por serie.</li>" +
                            "   <li>🔥 <b>Superseries y dropsets:</b> Para máxima fatiga muscular.</li>" +
                            "   <li>🔥 <b>Cardio post-entrenamiento:</b> 20-30 minutos de LISS o HIIT.</li>" +
                            "</ul>" +

                            "<h3>Estiramientos y Movilidad</h3>" +
                            "<p>Los estiramientos ayudan a prevenir lesiones, mejorar la flexibilidad y optimizar el rendimiento. Se recomienda realizarlos después del entrenamiento para relajar los músculos y mejorar la recuperación.</p>" +

                            "<h4>🔹 Estiramientos para el Tren Superior</h4>" +
                            "<ul>" +
                            "   <li>🧘 <b>Estiramiento de pectorales:</b> Apoya la mano en una pared con el codo a 90° y gira lentamente el torso en la dirección opuesta hasta sentir la tensión en el pecho. Mantén 20-30 segundos por lado.</li>" +
                            "   <li>🧘 <b>Estiramiento de tríceps:</b> Lleva un brazo por detrás de la cabeza y presiona el codo con la mano opuesta.</li>" +
                            "   <li>🧘 <b>Estiramiento de espalda alta:</b> Agarra un poste o una barra y flexiona el torso hacia atrás, sintiendo la extensión en la parte superior de la espalda.</li>" +
                            "   <li>🧘 <b>Estiramiento de bíceps:</b> Extiende el brazo hacia atrás con la palma hacia afuera apoyándola en una pared.</li>" +
                            "</ul>" +

                            "<h4>🔹 Estiramientos para el Tren Inferior</h4>" +
                            "<ul>" +
                            "   <li>🦵 <b>Estiramiento de cuádriceps:</b> De pie, lleva un pie hacia el glúteo sujetándolo con la mano. Mantén la espalda recta y presiona suavemente el talón contra el glúteo.</li>" +
                            "   <li>🦵 <b>Estiramiento de isquiotibiales:</b> Sentado con una pierna extendida, inclina el torso hacia adelante manteniendo la espalda recta.</li>" +
                            "   <li>🦵 <b>Estiramiento de glúteos:</b> Cruza una pierna sobre la otra y lleva la rodilla hacia el pecho.</li>" +
                            "   <li>🦵 <b>Estiramiento de gemelos:</b> Apoya las manos contra una pared y empuja el talón hacia atrás sin levantarlo del suelo.</li>" +
                            "</ul>" +

                            "<h4>🔹 Movilidad Articular</h4>" +
                            "<ul>" +
                            "   <li>🔄 <b>Círculos de hombros:</b> Gira los hombros hacia adelante y hacia atrás en movimientos controlados.</li>" +
                            "   <li>🔄 <b>Movilidad de cadera:</b> Realiza círculos con la pierna para mejorar la movilidad.</li>" +
                            "   <li>🔄 <b>Rotaciones de cuello:</b> Gira la cabeza lentamente en círculos pequeños para liberar tensión.</li>" +
                            "</ul>"+

                            "<h3>Nutrición para Ganancia Muscular</h3>" +
                            "<p>Una alimentación adecuada es clave para maximizar el crecimiento muscular. Se recomienda una dieta rica en proteínas, carbohidratos complejos y grasas saludables.</p>" +

                            "<h4>🔹 Proteínas: Construcción Muscular</h4>" +
                            "<ul>" +
                            "   <li>🥩 <b>Fuentes animales:</b> Pollo, carne magra, pescado (salmón, atún), huevos, lácteos.</li>" +
                            "   <li>🌱 <b>Fuentes vegetales:</b> Legumbres (lentejas, garbanzos), tofu, quinoa, frutos secos.</li>" +
                            "   <li>💡 <b>Consejo:</b> Consumir proteína cada 3-4 horas para optimizar la síntesis muscular.</li>" +
                            "</ul>" +

                            "<h4>🔹 Carbohidratos: Energía y Rendimiento</h4>" +
                            "<ul>" +
                            "   <li>🥔 <b>Carbohidratos complejos:</b> Arroz integral, patatas, avena, quinoa.</li>" +
                            "   <li>🍌 <b>Carbohidratos rápidos:</b> Frutas, miel, dátiles (ideales post-entrenamiento).</li>" +
                            "   <li>💡 <b>Consejo:</b> Asegurar una buena carga de carbohidratos antes del entrenamiento para maximizar la energía.</li>" +
                            "</ul>" +

                            "<h4>🔹 Grasas Saludables: Regulación Hormonal</h4>" +
                            "<ul>" +
                            "   <li>🥑 <b>Fuentes:</b> Aguacate, frutos secos, aceite de oliva, semillas de chía.</li>" +
                            "   <li>💡 <b>Consejo:</b> Incluir grasas saludables en la cena para favorecer la recuperación nocturna.</li>" +
                            "</ul>" +

                            "<h4>🔹 Hidratación y Suplementación</h4>" +
                            "<ul>" +
                            "   <li>💧 <b>Hidratación:</b> Bebe al menos 2.5L de agua al día. La deshidratación reduce la fuerza y la recuperación muscular.</li>" +
                            "   <li>💊 <b>Suplementos recomendados:</b></li>" +
                            "   <ul>" +
                            "       <li>🔹 <b>Proteína en polvo:</b> Útil si no alcanzas tus requerimientos diarios.</li>" +
                            "       <li>🔹 <b>Creatina:</b> Mejora la fuerza y la resistencia muscular.</li>" +
                            "       <li>🔹 <b>Aminoácidos esenciales (BCAA):</b> Ayudan a reducir la fatiga y mejoran la recuperación.</li>" +
                            "   </ul>" +
                            "</ul>" +

                            "<h3>Recuperación y Optimización</h3>" +
                            "<p>El descanso y la recuperación son igual de importantes que el entrenamiento. Sin una adecuada recuperación, el crecimiento muscular se verá afectado.</p>" +

                            "<h4>🔹 Sueño y Descanso</h4>" +
                            "<ul>" +
                            "   <li>🛌 <b>Duración recomendada:</b> 7-9 horas por noche.</li>" +
                            "   <li>🌙 <b>Evita pantallas antes de dormir:</b> La luz azul afecta la producción de melatonina.</li>" +
                            "</ul>" +

                            "<h4>🔹 Técnicas de Recuperación</h4>" +
                            "<ul>" +
                            "   <li>🧊 <b>Baños de contraste:</b> Alternar agua fría y caliente mejora la circulación y reduce la inflamación.</li>" +
                            "   <li>💆 <b>Masajes con foam roller:</b> Ayudan a eliminar nudos musculares y mejorar la elasticidad.</li>" +
                            "   <li>🧘 <b>Yoga y estiramientos dinámicos:</b> Ideales para mejorar la movilidad y reducir la rigidez muscular.</li>" +
                            "</ul>" +

                            "<h4>🔹 Comida Post-Entrenamiento</h4>" +
                            "<ul>" +
                            "   <li>🍗 <b>Proteína + Carbohidratos:</b> Ayuda a reparar las fibras musculares y reponer glucógeno.</li>" +
                            "   <li>🍌 <b>Ejemplo de comida post-entrenamiento:</b> Pechuga de pollo con arroz integral y verduras.</li>" +
                            "</ul>" +

                            "<h4>🔹 Estrategias para Evitar el Sobreentrenamiento</h4>" +
                            "<ul>" +
                            "   <li>🛑 <b>Escucha a tu cuerpo:</b> Si sientes fatiga extrema, descansa.</li>" +
                            "   <li>📆 <b>Incluye días de descanso activo:</b> Caminatas suaves o natación ligera.</li>" +
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
