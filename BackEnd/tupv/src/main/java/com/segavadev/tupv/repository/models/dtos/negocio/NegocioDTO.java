package com.segavadev.tupv.repository.models.dtos.negocio;

import java.util.List;

import com.segavadev.tupv.repository.models.dtos.factura.FacturaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NegocioDTO {

    private String nit;

    private String nombre;

    private String telefono;

    private String email;

    private String direccion;

    private double iva;

    private double inversion;

    private List<FacturaDTO> facturas;
    
}
