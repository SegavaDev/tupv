package com.segavadev.tupv.repository.models.dtos.factura;

import java.util.Date;
import java.util.List;

import com.segavadev.tupv.repository.models.dtos.itemFacturaDTO.ItemFacturaDTO;
import com.segavadev.tupv.repository.models.dtos.negocio.NegocioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FacturaDTO {

    private long referencia;

    private Date fecha;

    private String dniCliente;

    private NegocioDTO negocio;

    private List<ItemFacturaDTO> items;

    private double totalNeto;

    private double totalDescuento;

    private double totalPagar;

    private double paga;

    private double devolucion;
    
}
