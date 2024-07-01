package com.segavadev.tupv.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.segavadev.tupv.repository.models.entities.TipoProducto;
import java.util.Optional;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {

    Optional<TipoProducto> findById(long id);

    Optional<TipoProducto> findByNombre(String nombre);
    
}
