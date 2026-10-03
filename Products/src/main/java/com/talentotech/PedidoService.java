package com.talentotech;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.talentotech.excepciones.EntradaInvalidaExcepcion;
import com.talentotech.excepciones.ProductoNoEncontradoExcepcion;
import com.talentotech.excepciones.StockInsuficienteExcepcion;

public class PedidoService {
    private List<Pedido> pedidos = new ArrayList<>();
    private ProductoService productoService;

    public PedidoService(ProductoService productoService){
        this.productoService = productoService;
        }
        
    public void crearPedido(Scanner ingreso) {
        System.out.println("Usuario: ");
        String user = ingreso.nextLine();
        System.out.println("Email: ");
        String email = ingreso.nextLine();
        Cliente clientX = new Cliente(user, email);

        Pedido pedido = new Pedido(clientX);

        boolean seguirAgregando = true;
        while (seguirAgregando) {
            try {
                Producto producto = productoService.buscarPorID(ingreso);
                System.out.println("¿Cuantas unidades desea?: ");
                int cantidad = ingreso.nextInt();
                ingreso.nextLine();

                pedido.agregarProducto(producto, cantidad);
                System.out.println("Agregado al pedido");
            } catch(ProductoNoEncontradoExcepcion | EntradaInvalidaExcepcion | StockInsuficienteExcepcion e){
                System.out.println("No se pudo cargar el producto. Error: "+e.getMessage());
                continue;
            }    
            boolean respuestaValida = false;
            while (!respuestaValida){
                System.out.println("¿Desea agregar otro producto? [Si/No]");
                String respuesta = ingreso.nextLine();
                if (respuesta != null && !respuesta.isEmpty() && respuesta.toLowerCase().substring(0, 1).equals("s")){
                    respuestaValida = true;
                } else if (respuesta != null && !respuesta.isEmpty() && respuesta.toLowerCase().substring(0, 1).equals("n")){
                    System.out.println("Finalizando el pedido");
                    seguirAgregando = false;
                    respuestaValida = true;
                } else {
                    System.out.println("Ingrese Si/No para respuesta");
                }
            }   
        }

    pedidos.add(pedido);
    System.out.println("Pedido creado. Total: " + pedido.calcularTotal());
    }

    public void listarPedido(){
        if (pedidos.isEmpty()){
            System.out.println("Todavia no hay productos cargados en el pedido");
            return;
        }
        for (Pedido p : pedidos){
            p.mostrarPedido();
            System.out.println("-----------------------------------");
        }
    }
}
