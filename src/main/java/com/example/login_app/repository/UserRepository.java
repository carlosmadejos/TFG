package com.example.login_app.repository;

import com.example.login_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
