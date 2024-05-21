package com.ferremas.ferremas.controller;

import com.ferremas.ferremas.dto.CategoriaRequest;
import com.ferremas.ferremas.dto.CategoriaResponse;
import com.ferremas.ferremas.exception.RequestException;
import com.ferremas.ferremas.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> obtenerCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerCategorias());
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> crearCategoria(@RequestBody CategoriaRequest request) {
        HashMap<String, String> errors = new HashMap<>();
        if(request.getNombre() == null || request.getNombre().isEmpty()) {
            errors.put("nombre", "Debes ingresar un nombre a la categoría.");
        }
        if(request.getImagen() == null || request.getImagen().isEmpty()) {
            errors.put("imagen", "Debes ingresar una imagen a la categoría.");
        }
        if(request.getDescripcion() == null || request.getDescripcion().isEmpty()) {
            errors.put("descripcion", "Debes ingresar una descripción a la categoría.");
        }
        if (!errors.isEmpty()) throw new RequestException("Ocurrieron errores al procesar su solicitud.", HttpStatus.BAD_REQUEST, errors);

        return ResponseEntity.ok(categoriaService.crearCategoria(request));
    }
}
