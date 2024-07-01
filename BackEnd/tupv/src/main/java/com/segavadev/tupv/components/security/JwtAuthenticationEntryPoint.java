package com.segavadev.tupv.components.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Anotamos la clase con @Component para que sea un bean administrado por Spring
@Component
// La clase implementa AuthenticationEntryPoint, que es utilizado para manejar
// casos en los que se requiere autenticación pero no se proporciona o es inválida.
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Sobrescribimos el método commence, que será invocado cuando ocurra una excepción
    // de autenticación.
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Enviamos un error de respuesta HTTP 401 (No Autorizado) con un mensaje "Unauthorized"
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
    
}