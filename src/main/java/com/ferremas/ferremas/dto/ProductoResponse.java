package com.ferremas.ferremas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoResponse {
    Integer id;
    String nombre;
    String descripcion;
    String color;
    Integer stock;
    Integer precio;
    String imagen;
    CategoriaResponse categoria;
}