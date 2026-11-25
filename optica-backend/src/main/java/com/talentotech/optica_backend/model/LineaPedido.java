package com.talentotech.optica_backend.model;

import java.lang.annotation.Inherited;
import java.math.BigDecimal;

import javax.annotation.processing.Generated;

import com.talentotech.optica_backend.exceptions.*;
import jakarta.persistence.*;

@Entity
@Table(name="linea_pedido")
public class LineaPedido {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private int cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    protected LineaPedido(){};
    
    public LineaPedido(Pedido pedido, Producto producto, int cantidad) {
        if (pedido == null || producto == null || cantidad <= 0) {
            throw new EntradaInvalidaExcepcion("No hay suficiente stock de " + producto.getNombre());
        }
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
    // public BigDecimal calcularSubtotal() {
    //     return producto.getPrecio()*cantidad;
    // }

    @Override 
    public String toString() {
        return producto.getNombre() + " x" + cantidad+ " = $ "+ subtotal;
    }
}
