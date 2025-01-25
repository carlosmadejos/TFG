package com.example.login_app.repository;

import com.example.login_app.model.Progress;
import com.example.login_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    // Buscar el progreso por usuario
    List<Progress> findByUser(User user);
}
