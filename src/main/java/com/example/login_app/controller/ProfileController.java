package com.example.login_app.controller;

import com.example.login_app.model.Achievement;
import com.example.login_app.model.User;
import com.example.login_app.model.Progress;
import com.example.login_app.model.UserAchievement;
import com.example.login_app.repository.UserRepository;
import com.example.login_app.repository.ProgressRepository;
import com.example.login_app.service.AchievementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AchievementService achievementService;

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Progress> progressList = progressRepository.findByUser(user);
        if (progressList == null) {
            progressList = new ArrayList<>();
        }
        System.out.println("Número de registros de progreso recuperados: " + progressList.size());

        List<Map<String, Object>> progressListJson = new ArrayList<>();
        for (Progress progress : progressList) {
            Map<String, Object> progressMap = new HashMap<>();
            progressMap.put("date", progress.getDate().toString());
            progressMap.put("weight", progress.getWeight());
            progressMap.put("height", progress.getHeight());
            progressListJson.add(progressMap);
        }
        System.out.println("JSON generado para progressListJson: " + progressListJson);


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(progressListJson);
            System.out.println("JSON generado por ObjectMapper: " + jsonString);

            model.addAttribute("progressListJson", jsonString);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializando progressList a JSON: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("progressListJson", "[]");
        }

        model.addAttribute("calculatedAge", user.getAge());
        model.addAttribute("user", user);

        // Obtener los logros del usuario
        List<UserAchievement> userAchievements =
                achievementService.getUserAchievements(user);
        List<Achievement> allAchievements =
                achievementService.getAllAchievements();

        model.addAttribute("userAchievements", userAchievements);
        model.addAttribute("allAchievements", allAchievements);

        return "profile";
    }


    @PostMapping("/profile/update")
    public String updateUserProfile(@ModelAttribute User updatedUser, Model model) {

        // Obtener el usuario autenticado
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar el usuario con los nuevos valores
        currentUser.setWeight(updatedUser.getWeight());
        currentUser.setHeight(updatedUser.getHeight());
        userRepository.save(currentUser);

        // Guardar el progreso en la base de datos
        Progress progress = new Progress();
        progress.setWeight(updatedUser.getWeight());
        progress.setHeight(updatedUser.getHeight());
        progress.setDate(LocalDate.now()); // Establecer la fecha actual
        progress.setUser(currentUser); // Relacionar el progreso con el usuario

        progressRepository.save(progress); // Guardar el progreso en la base de datos

        // Evaluar logros tras actualizar progreso
        achievementService.evaluateWeightLossAchievements(currentUser);

        // Redirigir al perfil
        return "redirect:/profile";
    }

    @PostMapping("/profile/update-password")
    public String updatePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model, Principal principal) {

        // Obtener el usuario autenticado
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Añadir el usuario al modelo (para renderizar la vista si hay errores)
        model.addAttribute("user", user);

        // Recuperar la lista de progreso para el modelo
        List<Progress> progressList = progressRepository.findByUser(user);
        if (progressList == null) {
            progressList = new ArrayList<>();
        }

        List<Map<String, Object>> progressListJson = new ArrayList<>();
        for (Progress progress : progressList) {
            Map<String, Object> progressMap = new HashMap<>();
            progressMap.put("date", progress.getDate().toString());
            progressMap.put("weight", progress.getWeight());
            progressMap.put("height", progress.getHeight());
            progressListJson.add(progressMap);
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(progressListJson);
            model.addAttribute("progressListJson", jsonString);
        } catch (JsonProcessingException e) {
            model.addAttribute("progressListJson", "[]");
        }

        // Verificar que la contraseña actual es correcta
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("error", "La contraseña actual es incorrecta");
            return "profile";  // Mostrar error si la contraseña actual no es correcta
        }

        // Verificar que la nueva contraseña y la confirmación coinciden
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden");
            return "profile";  // Mostrar error si las contraseñas no coinciden
        }

        // Encriptar la nueva contraseña
        user.setPassword(passwordEncoder.encode(newPassword));

        // Guardar el usuario actualizado con la nueva contraseña
        userRepository.save(user);

        model.addAttribute("success", "Contraseña actualizada exitosamente");

        return "redirect:/profile";  // Redirigir al perfil actualizado
    }

}
