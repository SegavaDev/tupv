package com.segavadev.tupv.repository.models.dtos.persona;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PersonaDTO {

    private String dni;
    private String p_nombre;
    private String s_nombre;
    private String p_apellido;
    private String s_apellido;
    private String email;
    private Date fecha_nacimiento;
    
}
