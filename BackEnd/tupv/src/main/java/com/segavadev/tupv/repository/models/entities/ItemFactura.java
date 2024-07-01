package com.segavadev.tupv.repository.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Items_facturas")
public class ItemFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Null(message = "Debe relacionarse a una factura")
    @ManyToOne
    @JoinColumn(
        name = "id_factura"
    )
    private Factura factura;

    @ManyToOne
    @JoinColumn(
        name = "id_producto"
    )
    private Producto producto;

    @Positive(message = "La cantidad no puede ser negativa")
    private long cantidad;

    @Positive(message = "El precio neto no puede ser negativo")
    private double precioNeto;

    @Positive(message = "El precio total no puede ser negativo")
    private double precioTotal;
    
}
