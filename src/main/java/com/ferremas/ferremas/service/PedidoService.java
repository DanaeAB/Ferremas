package com.ferremas.ferremas.service;

import com.ferremas.ferremas.dto.*;
import com.ferremas.ferremas.exception.RequestException;
import com.ferremas.ferremas.model.DetallePedido;
import com.ferremas.ferremas.model.Pedido;
import com.ferremas.ferremas.model.Producto;
import com.ferremas.ferremas.model.Usuario;
import com.ferremas.ferremas.repository.PedidoRepository;
import com.ferremas.ferremas.repository.ProductoRepository;
import com.ferremas.ferremas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoResponse crearPedido(PedidoRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(request.getUsuarioId());
        if (usuarioOpt.isEmpty()) {
            throw new RequestException("El usuario no existe.", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioOpt.get();
        List<DetallePedido> detallePedidos = new ArrayList<>();

        Pedido pedido = Pedido.builder()
                .direccion(request.getDireccion())
                .usuario(usuario)
                .build();

        for (DetallePedidoRequest dpRequest: request.getProductos()) {
            Optional<Producto> productoOpt = productoRepository.findById(dpRequest.getProductoId());
            if (productoOpt.isEmpty()) {
                throw new RequestException("El producto seleccionado no existe.", HttpStatus.BAD_REQUEST);
            }
            Producto producto = productoOpt.get();

            // Verificar si hay suficiente stock
            if (producto.getStock() < dpRequest.getCantidad()) {
                throw new RequestException("No hay suficiente stock para el producto: " + producto.getNombre(), HttpStatus.BAD_REQUEST);
            }

            // Descontar el stock del producto
            producto.setStock(producto.getStock() - dpRequest.getCantidad());

            // Guardar el producto actualizado
            productoRepository.save(producto);

            DetallePedido detallePedido = DetallePedido.builder()
                    .producto(producto)
                    .cantidad(dpRequest.getCantidad())
                    .precioTotal(dpRequest.getCantidad() * producto.getPrecio())
                    .pedido(pedido)
                    .build();

            detallePedidos.add(detallePedido);
        }
        pedido.setTotal(detallePedidos.stream().mapToInt(DetallePedido::getPrecioTotal).reduce(0, Integer::sum));
        pedido.setDetalles(detallePedidos);

        pedidoRepository.save(pedido);


        return PedidoResponse.builder()
                .id(pedido.getId())
                .usuarioId(pedido.getUsuario().getId())
                .direccion(pedido.getDireccion())
                .detalles(detallePedidos.stream().map(dP -> DetallePedidoResponse.builder()
                        .id(dP.getId())
                        .cantidad(dP.getCantidad())
                        .producto(ProductoResponse.builder()
                                .nombre(dP.getProducto().getNombre())
                                .stock(dP.getProducto().getStock())
                                .color(dP.getProducto().getColor())
                                .descripcion(dP.getProducto().getDescripcion())
                                .imagen(dP.getProducto().getImagen())
                                .id(dP.getProducto().getId())
                                .precio(dP.getProducto().getPrecio())
                                .categoria(CategoriaResponse.builder()
                                        .nombre(dP.getProducto().getCategoria().getNombre())
                                        .imagen(dP.getProducto().getCategoria().getImagen())
                                        .descripcion(dP.getProducto().getCategoria().getDescripcion())
                                        .id(dP.getProducto().getCategoria().getId())
                                        .build())
                                .build()
                        )
                        .precioTotal(dP.getPrecioTotal())
                        .build()).toList())
                .precioTotal(pedido.getTotal())
                .build();
    }

    public List<PedidoResponse> obtenerPedidosUsuario(Integer userId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioIdOrderByIdDesc(userId);

        return pedidos.stream().map(pedido -> new PedidoResponse(
                pedido.getId(),
                pedido.getUsuario().getId(),
                pedido.getDireccion(),
                pedido.getDetalles().stream().map(dp -> DetallePedidoResponse.builder()
                        .id(dp.getId())
                        .cantidad(dp.getCantidad())
                        .precioTotal(dp.getPrecioTotal())
                        .producto(ProductoResponse.builder()
                                .nombre(dp.getProducto().getNombre())
                                .stock(dp.getProducto().getStock())
                                .color(dp.getProducto().getColor())
                                .descripcion(dp.getProducto().getDescripcion())
                                .imagen(dp.getProducto().getImagen())
                                .id(dp.getProducto().getId())
                                .precio(dp.getProducto().getPrecio())
                                .categoria(CategoriaResponse.builder()
                                        .nombre(dp.getProducto().getCategoria().getNombre())
                                        .imagen(dp.getProducto().getCategoria().getImagen())
                                        .descripcion(dp.getProducto().getCategoria().getDescripcion())
                                        .id(dp.getProducto().getCategoria().getId())
                                        .build())
                                .build())
                        .build()).toList(),
                pedido.getTotal()
        )).toList();
    }
}
