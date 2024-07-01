package com.segavadev.tupv.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.segavadev.tupv.repository.models.entities.Producto;
import com.segavadev.tupv.repository.models.entities.TipoProducto;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findById(long id);

    Optional<Producto> findByCodigo(long codigo);

    Optional<Producto> findByTipo(TipoProducto tipo);

    Optional<Producto> findByDescuento(double descuento);

    Optional<Producto> findByNombre(String nombre);
    
}
