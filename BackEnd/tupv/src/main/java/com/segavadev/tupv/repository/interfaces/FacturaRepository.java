package com.segavadev.tupv.repository.interfaces;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.segavadev.tupv.repository.models.entities.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findById(long id);
    
    Optional<Factura> findByReferencia(long referencia);

    Optional<Factura> findByFecha(Date fecha);

    Optional<Factura> findByDniCliente(String dniCliente);
    
}
