package com.segavadev.tupv.controllers.authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
// Importaciones necesarias para las clases y servicios usados en el controlador
import org.springframework.http.ResponseEntity; // Para manejar respuestas HTTP
import org.springframework.security.authentication.AuthenticationManager; // Para autenticar usuarios
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Token de autenticación basado en nombre de usuario y contraseña
import org.springframework.security.core.Authentication; // Representa una autenticación de un usuario
import org.springframework.security.core.AuthenticationException; // Excepción lanzada durante la autenticación
import org.springframework.security.core.userdetails.UserDetails; // Proporciona detalles del usuario
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*; // Anotaciones para controlar solicitudes HTTP

import com.segavadev.tupv.repository.models.dtos.persona.PersonaDTOcrear;
import com.segavadev.tupv.services.jwt.JWTService; // Servicio para generar tokens JWT
import com.segavadev.tupv.services.persona.PersonaServices;
import com.segavadev.tupv.utils.errors.DataRepetida;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor; // Genera un constructor con todos los campos como parámetros

@RestController // Anotación para indicar que esta clase es un controlador REST
@RequestMapping("/api/v1/auth") // Mapea las solicitudes a la ruta base "/auth"
@AllArgsConstructor // Genera un constructor con todos los campos como parámetros
public class AuthenticationController {

    //* Servicios */
    private final AuthenticationManager authenticationManager; // Se usa para autenticar a los usuarios
    private final JWTService jwtService; // Se usa para generar tokens JWT
    private final PersonaServices personaServices;

    /**
     * Endpoint para manejar el inicio de sesión de los usuarios.
     * @param username Nombre de usuario proporcionado en la solicitud.
     * @param password Contraseña proporcionada en la solicitud.
     * @return Respuesta HTTP con el token JWT si la autenticación es exitosa, o un mensaje de error en caso contrario.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            // Intentar autenticar al usuario usando el AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            // Obtener detalles del usuario autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generar un token JWT usando el JWTService
            String jwt = jwtService.generateToken(userDetails);

            // Devolver una respuesta HTTP 200 con el token JWT en el encabezado "Authorization"
            return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body("Login successful");

        } catch (AuthenticationException e) {
            // Manejar la excepción de autenticación fallida
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }

    /**
     * Endpoint para manejar el registro de nuevos usuarios.
     * @param username Nombre de usuario proporcionado en la solicitud.
     * @param password Contraseña proporcionada en la solicitud.
     * @return Respuesta HTTP con un mensaje de éxito de registro.
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registrar(@Valid @RequestBody PersonaDTOcrear persona, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errores = result.getFieldErrors()
                .stream()
                .map(
                    error -> error.getField() + " " + error.getDefaultMessage()
                )
                .collect(Collectors.toList());

            response.put("Errores", errores);
            
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            try {
                personaServices.registrarPersona(persona);
                response.put("Mensaje", "El usuario ha sido registrado con éxito.");
                
            }
            catch (DataRepetida r) {
                response.put("Error", r.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
            catch (Exception e) {
                response.put("Error", "Ha ocurrido un error inesperado");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Devolver una respuesta HTTP 200 con un mensaje de éxito
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}