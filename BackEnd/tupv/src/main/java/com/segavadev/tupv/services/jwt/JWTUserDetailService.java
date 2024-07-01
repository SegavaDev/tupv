package com.segavadev.tupv.services.jwt;

import org.springframework.stereotype.Service;

import com.segavadev.tupv.repository.interfaces.PersonaRepository;
import com.segavadev.tupv.repository.models.entities.Persona;

import lombok.AllArgsConstructor;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** 
 ** Servicio que implementa UserDetails para cargar detalles del usuario basado en el email * 
 */

@Service
@AllArgsConstructor
public class JWTUserDetailService implements UserDetailsService {
	
	//* Repositorio para acceder a la base de datos de la persona */
	private final PersonaRepository personaRepository;

	/** 
	 ** Carga los detalles del usuario por su nombre de usuario (email)
	 * 
	 * @param username : El email de usuario del cual se obtendrán los detalles del usuario
	 * @return : Un objeto UserDetails con la información del usuario
	 * @throws UsernameNotFoundException : Si el usuario no es encontrado con el email proporcionado
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Busca a la persona en la base de datos por su email 
		Persona persona = this.personaRepository.findByEmail(username)
		.orElseThrow(() -> new UsernameNotFoundException("Usuario con email " + username + " no encontrado."));

		// Se crea una autoridad básica
		GrantedAuthority authority = new SimpleGrantedAuthority("USER");

		// Se retorna el usuario cpm ña contraseña y la autoridad
		return new User(persona.getEmail(), persona.getPassword(), Collections.singletonList(authority));

	}

}
