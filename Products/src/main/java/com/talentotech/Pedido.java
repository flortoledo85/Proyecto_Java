package com.talentotech;
import java.util.ArrayList;

public class Pedido {
    private ArrayList<Producto> productos;
    private Cliente cliente;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.productos = new ArrayList<>();
    }
    
    public void agregarProducto(Producto p){
        productos.add(p);
    }

    public Double calcularTotal() {
        Double total = 0;
        for (Producto p : productos) {
            total += p.getPrice() * p.getStock();
        }
        return total;
    }
}
