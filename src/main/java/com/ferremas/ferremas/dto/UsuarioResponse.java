package com.ferremas.ferremas.dto;

import com.ferremas.ferremas.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioResponse {
    Integer id;
    String nombre;
    String apellido;
    String email;
    String direccion;
    Role role;
}
