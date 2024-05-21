package com.ferremas.ferremas.dto;

import com.ferremas.ferremas.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoResponse {
    Integer id;
    Integer usuarioId;
    String direccion;
    List<DetallePedidoResponse> detalles;
    Integer precioTotal;
}