package com.segavadev.tupv.repository.models.dtos.persona;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CambioPass {

    private String password;

    private String nueva_password;
    
}
