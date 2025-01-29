package com.example.login_app.repository;

import com.example.login_app.model.Exercise;
import com.example.login_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByUserAndDate(User user, LocalDate date);
    List<Exercise> findByUser(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM Exercise e WHERE e.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
