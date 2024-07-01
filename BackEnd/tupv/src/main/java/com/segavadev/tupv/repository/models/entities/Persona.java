package com.segavadev.tupv.repository.models.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 8, message = "El DNI debe contener al menos 8 dígitos")
    @Pattern(regexp = "\\d+", message = "El DNI debe ser solo numérico")
    private String dni;

    @Size(min = 2, message = "Se debe ingresar un nombre")
    private String p_nombre;
    private String s_nombre;

    @Size(min = 2, message = "Se debe ingresar un apellido")
    private String p_apellido;
    private String s_apellido;

    @Email(message = "Debe ingresar un formato válido de email, ejemplo: example@mail.com")
    private String email;

    @Temporal(value = TemporalType.DATE)
    private Date fecha_nacimiento;

    @Size(min = 8, message = "La contraseña debe ser de al menos 8 caracteres, dígitos o caracteres especiales")
    private String password;
    
}
