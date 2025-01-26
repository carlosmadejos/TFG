package com.example.login_app.repository;

import com.example.login_app.model.Exercise;
import com.example.login_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByUserAndDate(User user, LocalDate date);
    List<Exercise> findByUser(User user);
}
