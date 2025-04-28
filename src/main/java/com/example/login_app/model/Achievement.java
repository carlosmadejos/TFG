package com.example.login_app.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // e.g., "WEIGHT_LOSS"

    @Column(nullable = false)
    private String name; // e.g., "Perdedor de peso"

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double threshold; // kg to lose

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    public Achievement() {
        // constructor por defecto requerido por JPA
    }

    public Achievement(String code,
                       String name,
                       String description,
                       Double threshold,
                       Level level) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.threshold = threshold;
        this.level = level;
    }

    public enum Level {
        BRONZE,
        SILVER,
        GOLD
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return Objects.equals(code, that.code) && level == that.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, level);
    }
}

