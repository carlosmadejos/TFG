package com.example.login_app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "daily_logs")
public class DailyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Permitir múltiples registros por usuario
    private User user;


    @Column(nullable = false)
    private int calorieGoal = 2000; // Valor predeterminado

    @OneToMany(mappedBy = "dailyLog", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Food> foods; // Lista de alimentos en el registro diario

    @Column(nullable = false)
    private boolean closed = false; // Indica si el registro está archivado o activo

    @Column(nullable = false)
    private int totalCalories = 0; // Calorías consumidas en el día

    @Column(nullable = false)
    private int totalProteins = 0;

    @Column(nullable = false)
    private int totalCarbs = 0;

    @Column(nullable = false)
    private int totalFats = 0;



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

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getTotalCalories() {
        return totalCalories;
    }
    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getTotalProteins() { return totalProteins; }
    public void setTotalProteins(int totalProteins) { this.totalProteins = totalProteins; }

    public int getTotalCarbs() { return totalCarbs; }
    public void setTotalCarbs(int totalCarbs) { this.totalCarbs = totalCarbs; }

    public int getTotalFats() { return totalFats; }
    public void setTotalFats(int totalFats) { this.totalFats = totalFats; }


}
