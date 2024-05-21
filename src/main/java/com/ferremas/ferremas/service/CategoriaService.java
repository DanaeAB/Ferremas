package com.ferremas.ferremas.service;

import com.ferremas.ferremas.dto.CategoriaRequest;
import com.ferremas.ferremas.dto.CategoriaResponse;
import com.ferremas.ferremas.model.Categoria;
import com.ferremas.ferremas.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaResponse crearCategoria(CategoriaRequest request) {
        Categoria categoria = Categoria.builder()
                .nombre(request.getNombre())
                .imagen(request.getImagen())
                .descripcion(request.getDescripcion())
                .build();

        categoriaRepository.save(categoria);
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .imagen(categoria.getImagen())
                .descripcion(categoria.getDescripcion())
                .build();
    }

    public List<CategoriaResponse> obtenerCategorias() {
        return categoriaRepository.findAll().stream().map(categoria -> CategoriaResponse.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .imagen(categoria.getImagen())
                .descripcion(categoria.getDescripcion())
                .build()
        ).toList();
    }
}
