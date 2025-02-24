package com.example.login_app.repository;

import com.example.login_app.model.DailyLog;
import com.example.login_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.util.Optional;

public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {

    Optional<DailyLog> findByUser(User user);

    void deleteByUser(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM DailyLog d WHERE d.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Query("SELECT d FROM DailyLog d WHERE d.user = :user AND d.closed = true ORDER BY d.id DESC")
    List<DailyLog> findByUserAndClosedTrue(@Param("user") User user);

    // Encuentra los registros activos de un usuario
    List<DailyLog> findByUserAndClosedFalse(User user);

    Optional<DailyLog> findFirstByUserAndClosedFalseOrderByIdDesc(User user);

    @Query("SELECT d FROM DailyLog d WHERE d.user = :user ORDER BY d.createdAt DESC")
    List<DailyLog> findByUserOrderByCreatedAtDesc(@Param("user") User user);


}
