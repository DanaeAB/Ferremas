package com.ferremas.ferremas.repository;

import com.ferremas.ferremas.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioIdOrderByIdDesc(Integer usuarioId);
}
