package com.segavadev.tupv.configurations.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.segavadev.tupv.configurations.security.filtros.CsrfCookieFilter;
import com.segavadev.tupv.configurations.security.filtros.JWTValidationFilter;

/**
 ** Configuración de seguridad de spring security
 */

@Configuration
public class SecurityConfig {

    // Paths de swagger
    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources"
    };

    // Paths que no necesitan autenticación
    private static final String[] WHITELIST = {
            "/api/v1/auth/**",
            "/api/v1/public/**"
    };

    // Filtro de validación de tokens
    private final JWTValidationFilter jwtValidationFilter;

    public SecurityConfig(JWTValidationFilter jwtValidationFilter) {
        this.jwtValidationFilter = jwtValidationFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
        csrfTokenRepository.setCookieCustomizer(
                csrf -> csrf
                        .httpOnly(true)
                        .path("/")
                        .secure(false)
                        .maxAge(-1));
        csrfTokenRepository.setCookieName("XSRF-TOKEN"); // Nombre de la cookie

        http
            .csrf(csrf -> csrf
                    .csrfTokenRepository(csrfTokenRepository)
                    .csrfTokenRequestHandler(requestHandler))
            .addFilterAfter(new CsrfCookieFilter(), CsrfFilter.class)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura la política de sesión como sin estado
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(WHITELIST).permitAll() // Rutas públicas permitidas
                    .requestMatchers(SWAGGER_WHITELIST).permitAll() // Rutas de Swagger públicas permitidas
                    .anyRequest().authenticated() // Resto de rutas requieren autenticación
            )
            .addFilterBefore(jwtValidationFilter, BasicAuthenticationFilter.class) // Agrega el filtro de validación
                                                                                    // JWT antes del filtro de
                                                                                    // autenticación básica
            .httpBasic(basic -> basic.disable()) // Desactiva la autenticación básica
            .formLogin(form -> form.disable()) // Desactiva el inicio de sesión basado en formularios
            .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Configuración CORS

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Configura los orígenes permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Configura los métodos HTTP
                                                                                        // permitidos
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Configura los encabezados
                                                                                         // permitidos
        configuration.setAllowCredentials(true); // Permite el uso de credenciales (como cookies) en solicitudes CORS

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica la configuración CORS a todas las rutas
        return source;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}