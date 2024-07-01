package com.segavadev.tupv.configurations.security.filtros;

import org.springframework.lang.NonNull;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

/**
 * Filtro que añade un cookie CSRF a la respuesta HTTP.
 * Este filtro se asegura de que el token CSRF sea enviado al cliente como una cookie.
 */
public class CsrfCookieFilter extends OncePerRequestFilter {

    /**
     * Aplica el filtro a cada solicitud HTTP.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @param filterChain La cadena de filtros que se aplicarán a la solicitud.
     * @throws ServletException Si ocurre una excepción en el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Obtiene el token CSRF desde los atributos de la solicitud
        var csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if (Objects.nonNull(csrfToken)) {
            // Crea un nuevo cookie con el token CSRF
            Cookie csrfCookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
            csrfCookie.setPath("/"); // La cookie estará disponible en todo el dominio
            csrfCookie.setHttpOnly(true); // La cookie no será accesible desde JavaScript
            csrfCookie.setSecure(false); // Asegúrate de que esta cookie solo se envíe a través de HTTPS en producción
            csrfCookie.setMaxAge(-1); // La cookie expira cuando se cierra el navegador
            
            // Añade el cookie a la respuesta HTTP
            response.addCookie(csrfCookie);
        }

        // Continúa con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}