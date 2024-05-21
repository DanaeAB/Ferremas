package com.ferremas.ferremas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaRequest {
    String nombre;
    String imagen;
    String descripcion;
}
