package com.segavadev.tupv.repository.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value = 1, message = "El código debe ser mayor a 0")
    @Column(nullable = false, unique = true)
    private long codigo;

    @Size(min = 1, message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @Max(value = 100, message = "No puede dar un descuento mayor al 100%")
    @Positive
    private double descuento;

    @ManyToOne
    @JoinColumn(
        name = "TipoProducto_id"
    )
    private TipoProducto tipo;

    @Positive
    private long stock;

    @Positive
    private double precio;

    private String imagen;
    
}
