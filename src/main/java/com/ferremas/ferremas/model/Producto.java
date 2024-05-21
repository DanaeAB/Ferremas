package com.ferremas.ferremas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "producto")
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false)
    String nombre;
    @Column(nullable = false)
    String descripcion;
    @Column(nullable = false)
    String color;
    @Column(nullable = false)
    Integer stock;
    @Column(nullable = false)
    Integer precio;
    @Column(nullable = false)
    String imagen;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoria_id", nullable = false)
    Categoria categoria;
}
