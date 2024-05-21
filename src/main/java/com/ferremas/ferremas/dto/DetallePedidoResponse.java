package com.ferremas.ferremas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetallePedidoResponse {
    Integer id;
    ProductoResponse producto;
    Integer cantidad;
    Integer precioTotal;
}