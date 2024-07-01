package com.segavadev.tupv.configurations.security.filtros;

import java.io.IOException;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.segavadev.tupv.services.jwt.JWTService;
import com.segavadev.tupv.services.jwt.JWTUserDetailService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Filtro para validar tokens JWT en cada solicitud HTTP.
 * Este filtro extrae el token JWT del encabezado de autorización, valida el token y establece el contexto de seguridad.
 */
@Component
@AllArgsConstructor
@Slf4j
public class JWTValidationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final JWTUserDetailService jwtUserDetailService;

    // Constantes para los encabezados de autorización
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_HEADER_BEARER = "Bearer ";

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
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Obtiene el encabezado de autorización de la solicitud
        final var requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String jwt = null;

        // Verifica que el encabezado de autorización no sea nulo y comience con "Bearer "
        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith(AUTHORIZATION_HEADER_BEARER)) {
            // Extrae el token JWT del encabezado de autorización
            jwt = requestTokenHeader.substring(7);

            try {
                // Obtiene el nombre de usuario del token JWT
                username = jwtService.getUsernameFromToken(jwt);
            } catch (IllegalArgumentException e) {
                log.error("No se pudo obtener el token JWT: {}", e.getMessage());
            } catch (ExpiredJwtException e) {
                log.warn("El token JWT ha expirado: {}", e.getMessage());
            }
        }

        // Verifica si el nombre de usuario no es nulo y no hay una autenticación en el contexto de seguridad
        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            // Carga los detalles del usuario desde el servicio de detalles del usuario
            final var userDetails = this.jwtUserDetailService.loadUserByUsername(username);

            // Valida el token JWT
            if (this.jwtService.validateToken(jwt, userDetails)) {
                // Crea un objeto de autenticación con el usuario y sus autoridades
                var usernameAndPassAuthToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Establece los detalles de autenticación en el contexto de seguridad
                usernameAndPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernameAndPassAuthToken);
            }
        }

        // Continúa con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}

