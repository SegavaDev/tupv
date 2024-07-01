package com.segavadev.tupv.services.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.segavadev.tupv.configurations.security.FirmaSecreta;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm; //! SignatureAlgorithm.HS256 esta obsoleto, se debe buscar una alternativa
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    // Tiempo de validez del token en segundos
    public static final long JWT_TOKEN_VALIDITY = 10 * 60 * 60;

    // Firma secreta
    public static final String SECRET_KEY = FirmaSecreta.getFirma();

    /**
     ** Obtiene todos los Claims (datos) desde un token JWT.
     * 
     * @param token : El token JWT de donde se obtienen los datos (Claims)
     * @return : Los claims obtenidos del token
     * 
     */
    private Claims getAllClaimsFromToken(String token) {

        // Obtener la clave secreta
        final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        // Parsea el token y devuelve sus datos (Claims)
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /** 
     ** Obtiene un Claim especifico usando la función parámetro claimsResolver
     * 
     * @param token : El token JWT de donde se obtienen los datos (Claims)
     * @param claimsResolver : Resolver los Claims para obtener los datos dentro del token
     * @param <T> : Tipo de Claim a obtener
     * @return : El claim específico obtenido del token
     * 
    */
    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        // Obtiene todos los Claims desde el token
        final var claims = this.getAllClaimsFromToken(token);

        // Aplica el resolver y obtine un dato tipo T
        return claimsResolver.apply(claims);
    }

    /** 
     ** Obtiene el nombre de usuario (subject) desde el token
     * 
     * @param token : El token que proporciona el nombre del usuario
     * @return : El nombre del usuario obtenido desde el token
     * 
     */
    public String getUsernameFromToken(String token) {
        // Obtiene el nombre del usuario usando el resolverClaims
        return this.getClaimsFromToken(token, Claims::getSubject);
    }

    /** 
     ** Obtiene la fecha de expiración del token 
     * 
     * @param token : El token de donde sale la fecha de expiración
     * @return : La fecha de expiración
     * 
     */
    private Date getExpirationFromToken(String token) {
        // Returna la fecha de expiración desde el token usando el resolverClaims
        return this.getClaimsFromToken(token, Claims::getExpiration);
    }

    /** 
     ** Verifica si el token ya expiró
     * 
     * @param token
     * @return : true si el token ya expiró
     * 
     */
    private boolean isTokenExpired(String token) {
        // Obtiene la fecha de expiración del token
        final Date fechaExpiracion = this.getExpirationFromToken(token);

        // Verifica si está expirado y retorna el resultado
        return fechaExpiracion.before(new Date());
    }

    /** 
     ** Genera un nuevo token para el usuario proporcionado
     * 
     * @param username : El nombre de usuario para incluir en el token
     * @return : El token generado
     * 
     */
    public String generateToken(UserDetails username) {
        // Genera un nuevo token usando el nombre de usuario, la fecha de emisión y la fecha de expiración
        return this.generateToken(username.getUsername(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));
    }

    /** 
     ** Genera un nuevo token usando el nombre de usuario, la fecha de emisión y la fecha de expiración 
     * 
     * @param username : Nombre de usuario para el token
     * @param fechaEmision : Fecha en la que se creó el token
     * @param fechaExpira : Fecha de expiración del token
     * @return : Token generado
     * 
     */
    private String generateToken(String username, Date fechaEmision, Date fechaExpira) {
        // Obtiene la clave secreta
        final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        // Construye y firma el nuevo token con el nombre de usuario y las fechas de emisión y expiración
        return Jwts
        .builder()
        .subject(username)
        .issuedAt(fechaEmision)
        .expiration(fechaExpira)
        .signWith(key, SignatureAlgorithm.HS256) //! SignatureAlgorithm.HS256 esta obsoleto, se debe buscar una alternativa
        .compact();
    }

    /** 
     ** Valida si el token es valido para el nombre de usuario y si no ha expirado
     * 
     * @param token : El token de donde se sacará el nombre de usuario y se validara su expiración
     * @param username : El nombre del usuario a validar con el token
     * @return : true si el token es válido para el usuario y no ha expirado
     * 
     */
    public boolean validateToken(String token, UserDetails username) {
        // Obtiene el nombre de usuario desde el token
        final String jwtUsername = this.getUsernameFromToken(token);
        //Valida si el nombre de usuario coincide con el del token y si el token no ha expirado
        return (username.getUsername().equals(jwtUsername)) && !this.isTokenExpired(token);
    }

}