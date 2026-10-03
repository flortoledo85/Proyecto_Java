package com.talentotech;
import java.util.ArrayList;
import java.util.List;


public class Pedido {
    private List<LineaPedido> lineas;
    private Cliente cliente;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.lineas = new ArrayList<LineaPedido>();
    }
    
    public void agregarProducto(Producto p, int cantidad){
        LineaPedido l = new LineaPedido(p, cantidad);
        lineas.add(l);
    }

    public Double calcularTotal() {
        Double total = 0.;
        for (LineaPedido linea : lineas) {
            total += linea.calcularSubtotal();
        }
        return total;
    }

    public void mostrarPedido(){
        System.out.println("Cliente "+ cliente.getUser());
        for (LineaPedido linea: lineas) {
            System.out.println(linea);
        };
        System.out.println("Total: "+ calcularTotal());
    }
}
