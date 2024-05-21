package com.ferremas.ferremas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    String nombre;
    String apellido;
    String email;
    String password;
    String passwordConfirmation;
    String direccion;
}
