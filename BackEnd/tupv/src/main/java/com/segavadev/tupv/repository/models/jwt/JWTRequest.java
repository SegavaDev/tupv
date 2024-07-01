package com.segavadev.tupv.repository.models.jwt;

import lombok.Data;

@Data
public class JWTRequest {

    private String username;

    private String password;
    
}
