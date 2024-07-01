package com.segavadev.tupv.repository.models.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value = 1, message = "No se está generando una referencia")
    @Column(nullable = false, unique = true)
    private long referencia;

    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    @Size(min = 1, message = "EL DNI del cliente no puede estar vacío")
    private String dniCliente;

    @ManyToOne
    @JoinColumn(
        name = "id_negocio"
    )
    private Negocio negocio;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<ItemFactura> items;

    @Positive(message = "El total neto no puede ser negativo")
    private double totalNeto;

    @Max(value = 100, message = "El valor máximo de descuento es del 100%")
    @Min(value = 0, message = "El valor mínimo de descuento es 0")
    private double totalDescuento;

    @Positive(message = "El valor a pagar no puede ser negativo")
    @Column(nullable = false)
    private double totalPagar;

    @Positive(message = "El valor recibido no puede ser negativo")
    private double paga;

    @Positive(message = "El valor devuelto no puede ser negativo")
    private double devolucion;
    
}
