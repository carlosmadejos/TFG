package com.example.login_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "training_plans")
public class TrainingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String level;
    private int duration; // Duración en semanas

    @Lob
    @Column(columnDefinition = "TEXT") // Para almacenar contenido largo
    private String details; // Contenido detallado del plan

    public TrainingPlan() {}

    public TrainingPlan(String name, String description, String level, int duration, String details) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.duration = duration;
        this.details = details;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
