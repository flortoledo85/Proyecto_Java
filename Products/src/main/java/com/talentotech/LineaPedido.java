package com.talentotech;

import com.talentotech.excepciones.*;

public class LineaPedido {
    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        if (cantidad > producto.getStock()) {
            throw new StockInsuficienteExcepcion("No hay suficiente stock de " + producto.getName());
        }
        this.producto = producto;
        this.cantidad = cantidad;
        producto.descontarStock(cantidad);
    }

    public Double calcularSubtotal() {
        return producto.getPrice()*cantidad;
    }

    @Override 
    public String toString() {
        return producto.getName() + " x" + cantidad+ " = $ "+ calcularSubtotal();
    }
}
