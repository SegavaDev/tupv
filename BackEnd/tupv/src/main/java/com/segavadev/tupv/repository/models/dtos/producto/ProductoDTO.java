package com.segavadev.tupv.repository.models.dtos.producto;

import com.segavadev.tupv.repository.models.dtos.tipo_producto.TipoProductoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDTO {

    private long codigo;

    private String nombre;

    private double descuento;

    private TipoProductoDTO tipo;

    private long stock;

    private double precio;

    private String imagen;
    
}
