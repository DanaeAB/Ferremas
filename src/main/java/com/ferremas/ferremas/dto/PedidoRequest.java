package com.ferremas.ferremas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoRequest {
    Integer usuarioId;
    String direccion;
    List<DetallePedidoRequest> productos;
}
