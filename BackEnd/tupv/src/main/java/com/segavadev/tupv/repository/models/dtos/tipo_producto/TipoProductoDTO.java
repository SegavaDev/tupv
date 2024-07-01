package com.segavadev.tupv.repository.models.dtos.tipo_producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TipoProductoDTO {

    private String nombre;

    private String descripcion;
    
}
