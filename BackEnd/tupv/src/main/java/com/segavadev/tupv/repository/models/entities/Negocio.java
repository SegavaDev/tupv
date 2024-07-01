package com.segavadev.tupv.repository.models.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Negocios")
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 9, message = "El NIT debe tener mínimo 9 digitos")
    @Column(nullable = false, unique = true)
    private String nit;

    @Size(min = 1, message = "Debe ingresar un nombre para su negocio")
    @Column(nullable = false, unique = true)
    private String nombre;

    @Size(min = 10, message = "El teléfono debe tener mínimo 9 digitos")
    private String telefono;

    @Email(message = "El email ingresado no tiene el formato correcto, ejemplo: ejemplo@mail.com")
    @Column(unique = true)
    private String email;

    @Size(min = 1, message = "Debe ingresar una dirección")
    private String direccion;

    @Min(value = 0, message = "El valor mínimo de IVA es de 0")
    @Max(value = 100, message = "El valor máximo de IVA es del 100%")
    private double iva;

    private double inversion;

    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL)
    private List<Factura> facturas;

    public void setNit(String nit) {
        this.nit = nit.contains("-") ? nit.replaceAll("-", "") : nit;
    }
    
}
