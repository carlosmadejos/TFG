package com.example.login_app.repository;

import com.example.login_app.model.UserAchievement;
import com.example.login_app.model.User;
import com.example.login_app.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    List<UserAchievement> findByUser(User user);
    Optional<UserAchievement> findByUserAndAchievement(User user, Achievement achievement);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserAchievement ua WHERE ua.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
