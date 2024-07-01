package com.segavadev.tupv.repository.models.dtos.itemFacturaDTO;

import com.segavadev.tupv.repository.models.dtos.producto.ProductoDTOitemFactura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ItemFacturaDTO {

    private ProductoDTOitemFactura producto;

    private long cantidad;

    private double precioNeto;

    private double precioTotal;
    
}
