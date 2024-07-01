package com.segavadev.tupv.repository.models.dtos.producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDTOitemFactura {

    private long codigo;

    private String nombre;

    private double descuento;

    private double precio;
    
}
