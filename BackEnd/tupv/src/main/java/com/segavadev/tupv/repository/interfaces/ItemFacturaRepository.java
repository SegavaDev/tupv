package com.segavadev.tupv.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.segavadev.tupv.repository.models.entities.Factura;
import com.segavadev.tupv.repository.models.entities.ItemFactura;
import com.segavadev.tupv.repository.models.entities.Producto;

import java.util.Optional;

public interface ItemFacturaRepository extends JpaRepository<ItemFactura, Long> {

    Optional<ItemFactura> findById(long id);

    Optional<ItemFactura> findByProducto(Producto producto);

    Optional<ItemFactura> findByFactura(Factura factura);
    
}
