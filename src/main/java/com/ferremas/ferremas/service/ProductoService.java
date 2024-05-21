package com.ferremas.ferremas.service;

import com.ferremas.ferremas.dto.CategoriaResponse;
import com.ferremas.ferremas.dto.ProductoRequest;
import com.ferremas.ferremas.dto.ProductoResponse;
import com.ferremas.ferremas.exception.RequestException;
import com.ferremas.ferremas.model.Categoria;
import com.ferremas.ferremas.model.Producto;
import com.ferremas.ferremas.repository.CategoriaRepository;
import com.ferremas.ferremas.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public ProductoResponse crearProducto(ProductoRequest request) {
        Optional<Categoria> categoria = categoriaRepository.findById(request.getCategoria());
        if(categoria.isEmpty()) {
            throw new RequestException("La categoría ingresada no existe.", HttpStatus.BAD_REQUEST);
        }

        Producto producto = Producto.builder()
                .nombre(request.getNombre())
                .color(request.getColor())
                .precio(request.getPrecio())
                .descripcion(request.getDescripcion())
                .stock(request.getStock())
                .imagen(request.getImagen())
                .categoria(categoria.get())
                .build();

        productoRepository.save(producto);
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getColor(),
                producto.getStock(),
                producto.getPrecio(),
                producto.getImagen(),
                CategoriaResponse.builder()
                        .id(producto.getCategoria().getId())
                        .imagen(producto.getCategoria().getImagen())
                        .nombre(producto.getCategoria().getNombre())
                        .descripcion(producto.getCategoria().getDescripcion())
                        .build()
        );
    }

    public ProductoResponse obtenerProductoPorId(Integer id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isEmpty()) {
            throw new RequestException("El producto con id '" + id + "' no existe.", HttpStatus.NOT_FOUND);
        }

        Producto producto = productoOpt.get();
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getColor(),
                producto.getStock(),
                producto.getPrecio(),
                producto.getImagen(),
                CategoriaResponse.builder()
                        .id(producto.getCategoria().getId())
                        .imagen(producto.getCategoria().getImagen())
                        .nombre(producto.getCategoria().getNombre())
                        .descripcion(producto.getCategoria().getDescripcion())
                        .build()
        );
    }

    public List<ProductoResponse> obtenerProductos() {
        return productoRepository.findAll()
                .stream()
                .map(producto -> new ProductoResponse(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getColor(),
                        producto.getStock(),
                        producto.getPrecio(),
                        producto.getImagen(),
                        CategoriaResponse.builder()
                                .id(producto.getCategoria().getId())
                                .imagen(producto.getCategoria().getImagen())
                                .nombre(producto.getCategoria().getNombre())
                                .descripcion(producto.getCategoria().getDescripcion())
                                .build()
                ))
                .toList();
    }

    public List<ProductoResponse> obtenerProductosPorCategoria(Integer categoria) {
        return productoRepository.findByCategoriaId(categoria)
                .stream()
                .map(producto -> new ProductoResponse(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getColor(),
                        producto.getStock(),
                        producto.getPrecio(),
                        producto.getImagen(),
                        CategoriaResponse.builder()
                                .id(producto.getCategoria().getId())
                                .imagen(producto.getCategoria().getImagen())
                                .nombre(producto.getCategoria().getNombre())
                                .descripcion(producto.getCategoria().getDescripcion())
                                .build()
                ))
                .toList();
    }
}
