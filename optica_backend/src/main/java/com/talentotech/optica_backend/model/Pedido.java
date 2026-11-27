package com.talentotech.optica_backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Generated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String usuario;
    private String email;
    private LocalDateTime fecha;
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<LineaPedido> lineas = new ArrayList<>();

    protected Pedido() {
    };

    public Pedido(String usuario, String email) {
        this.usuario = usuario;
        this.email = email;
        this.fecha = LocalDateTime.now();
    }

    public void agregarProducto(Producto p, int cantidad) {
        LineaPedido linea = new LineaPedido(this, p, cantidad);
        lineas.add(linea);
        total = total.add(linea.getSubtotal());
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Integer getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }
    // public void mostrarPedido(){
    // System.out.println("Cliente "+ cliente.getUser());
    // for (LineaPedido linea: lineas) {
    // System.out.println(linea);
    // };
    // System.out.println("Total: "+ calcularTotal());
    // }
}
