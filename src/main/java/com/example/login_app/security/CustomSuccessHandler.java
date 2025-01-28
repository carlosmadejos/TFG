package com.example.login_app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;
import java.util.Collection;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // DEBUG: Imprimir los roles en la consola
        for (GrantedAuthority authority : authorities) {
            System.out.println("ROL DETECTADO: " + authority.getAuthority()); // 🔹 Esto te dirá qué está pasando
        }

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ADMIN")) { // Verifica ambos casos
                response.sendRedirect("/admin/dashboard");
                return;
            }
        }

        response.sendRedirect("/home");
    }
}
