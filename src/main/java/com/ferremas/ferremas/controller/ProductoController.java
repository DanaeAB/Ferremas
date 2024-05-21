package com.ferremas.ferremas.controller;

import com.ferremas.ferremas.dto.ProductoRequest;
import com.ferremas.ferremas.dto.ProductoResponse;
import com.ferremas.ferremas.exception.RequestException;
import com.ferremas.ferremas.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(@RequestBody ProductoRequest request) {
        HashMap<String, String> errors = new HashMap<>();

        if (request.getCategoria() == null) {
            errors.put("categoria", "Debes ingresar la categoria del producto.");
        }
        if(request.getNombre() == null || request.getNombre().isEmpty()) {
            errors.put("nombre", "Debes ingresar un nombre para el producto.");
        }
        if(request.getPrecio() == null) {
            errors.put("precio", "Debes ingresar un precio para el producto.");
        }
        if(request.getPrecio() != null && request.getPrecio() < 0) {
            errors.put("precio", "El precio no puede ser inferior a 0.");
        }
        if(request.getStock() == null) {
            errors.put("stock", "Debes ingresar la cantidad de stock disponible para el producto.");
        }
        if(request.getStock() != null && request.getStock() < 0) {
            errors.put("stock", "El stock no puede ser inferior a 0.");
        }
        if(request.getImagen() == null || request.getImagen().isEmpty()) {
            errors.put("imagen", "Debes ingresar la url de la imagen del producto.");
        }
        if(request.getColor() == null || request.getColor().isEmpty()) {
            errors.put("color", "Debes ingresar el color del producto.");
        }
        if(request.getDescripcion() == null || request.getDescripcion().isEmpty()) {
            errors.put("descripcion", "Debes ingresar la descripción del producto.");
        }
        if (!errors.isEmpty()) throw new RequestException("Ocurrieron errores al procesar su solicitud.", HttpStatus.BAD_REQUEST, errors);

        ProductoResponse productoResponse = productoService.crearProducto(request);
        return ResponseEntity.ok(productoResponse);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ProductoResponse> obtenerProducto(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> obtenerProductos(@RequestParam Optional<Integer> categoria) {
        if (categoria.isPresent()) {
            return ResponseEntity.ok(productoService.obtenerProductosPorCategoria(categoria.get()));
        }
        return ResponseEntity.ok(productoService.obtenerProductos());
    }
}
