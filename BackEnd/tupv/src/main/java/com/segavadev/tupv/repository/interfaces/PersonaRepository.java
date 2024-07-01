package com.segavadev.tupv.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.segavadev.tupv.repository.models.entities.Persona;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findById(long id);

    Optional<Persona> findByEmail(String email);

    Optional<Persona> findByDni(String dni);
    
}
