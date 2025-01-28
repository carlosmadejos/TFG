package com.example.login_app.repository;

import com.example.login_app.model.DailyLog;
import com.example.login_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {

    Optional<DailyLog> findByUser(User user);

    void deleteByUser(User user);
}
