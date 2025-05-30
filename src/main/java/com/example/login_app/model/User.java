package com.example.login_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username = "";

    @Column(nullable = false)
    @Email(message = "Debe proporcionar un email válido")
    private String email = "";

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password = "";

    @Column(nullable = false)
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate birthdate;

    @Column(nullable = false)
    @Min(value = 30, message = "El peso mínimo es 30 kg")
    @Max(value = 300, message = "El peso máximo es 300 kg")
    private Double weight = 30.0;

    @Column(nullable = false)
    @Min(value = 100, message = "La altura mínima es 100 cm")
    @Max(value = 250, message = "La altura máxima es 250 cm")
    private Double height = 100.0;

    @Column(nullable = false)
    private String role = "USER";

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer searchCount = 0;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        if (birthdate == null) return 0;
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
    }
}
