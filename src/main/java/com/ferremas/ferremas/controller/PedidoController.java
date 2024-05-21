package com.ferremas.ferremas.controller;

import com.ferremas.ferremas.dto.PedidoRequest;
import com.ferremas.ferremas.dto.PedidoResponse;
import com.ferremas.ferremas.exception.RequestException;
import com.ferremas.ferremas.service.JwtService;
import com.ferremas.ferremas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final JwtService jwtService;
    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> obtenerPedidos(@RequestHeader(value = "Authorization") String token) {
        Integer userId = jwtService.getSubjectFromToken(token.substring(7));
        List<PedidoResponse> pedidos = pedidoService.obtenerPedidosUsuario(userId);

        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@RequestHeader(value = "Authorization") String token, @RequestBody PedidoRequest request) {
        HashMap<String, String> errors = new HashMap<>();

        Integer userId = jwtService.getSubjectFromToken(token.substring(7));
        request.setUsuarioId(userId);

        if (request.getProductos() == null || request.getProductos().isEmpty()) {
            errors.put("productos", "Debes ingresar los productos a comprar.");
        }
        if (request.getDireccion() == null || request.getDireccion().isBlank()) {
            errors.put("domicilio", "Debes ingresar la dirección de tu domicilio.");
        }
        if (!errors.isEmpty()) throw new RequestException("Ocurrieron errores al procesar su solicitud.", HttpStatus.BAD_REQUEST, errors);

        return ResponseEntity.ok(pedidoService.crearPedido(request));
    }
}
