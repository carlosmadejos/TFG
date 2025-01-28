package com.example.login_app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "daily_logs")
public class DailyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // Relación con User
    private User user;

    @Column(nullable = false)
    private int calorieGoal = 2000; // Valor predeterminado

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String dailyLogJson; // Almacena el registro de alimentos como JSON

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public String getDailyLogJson() {
        return dailyLogJson;
    }

    public void setDailyLogJson(String dailyLogJson) {
        this.dailyLogJson = dailyLogJson;
    }
}
