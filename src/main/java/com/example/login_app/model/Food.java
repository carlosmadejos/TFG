package com.example.login_app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre del alimento
    private int calories; // Calorías por porción
    private int carbs; // Carbohidratos en gramos por porción
    private int proteins; // Proteínas en gramos por porción
    private int fats; // Grasas en gramos por porción
    private String imageUrl; // URL de la imagen del alimento

    @ManyToOne
    @JoinColumn(name = "daily_log_id") // Relación con DailyLog
    @JsonBackReference
    private DailyLog dailyLog;

    @Column(nullable = false)
    private int gramsConsumed = 100; // Por defecto, 100g

    // Constructor vacío
    public Food() {}

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DailyLog getDailyLog() {
        return dailyLog;
    }

    public void setDailyLog(DailyLog dailyLog) {
        this.dailyLog = dailyLog;
    }

    public int getGramsConsumed() {
        return gramsConsumed;
    }

    public void setGramsConsumed(int gramsConsumed) {
        this.gramsConsumed = gramsConsumed;
    }
}
