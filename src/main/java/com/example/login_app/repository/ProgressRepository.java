package com.example.login_app.repository;

import com.example.login_app.model.Progress;
import com.example.login_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    // Buscar el progreso por usuario
    List<Progress> findByUser(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM Progress p WHERE p.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
