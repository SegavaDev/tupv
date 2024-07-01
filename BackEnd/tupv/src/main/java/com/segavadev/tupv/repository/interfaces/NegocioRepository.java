package com.segavadev.tupv.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.segavadev.tupv.repository.models.entities.Negocio;

import java.util.Optional;

public interface NegocioRepository extends JpaRepository<Negocio, Long> {

    Optional<Negocio> findById(long id);

    Optional<Negocio> findByNit(String nit);

    Optional<Negocio> findByEmail(String email);

    Optional<Negocio> findByTelefono(String telefono);

    Optional<Negocio> findByDireccion(String direccion);
    
}
