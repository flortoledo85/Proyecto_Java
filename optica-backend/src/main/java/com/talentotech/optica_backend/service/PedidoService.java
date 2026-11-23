package com.talentotech.optica_backend.service;

import java.util.ArrayList;
import java.util.List;

import com.talentotech.optica_backend.excepciones;
import com.talentotech.optica_backend.model.Pedido;
import com.talentotech.optica_backend.model.Producto;
import com.talentotech.optica_backend.repository.PedidoRepository;
import com.talentotech.optica_backend.repository.ProductoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, Productorepository productoRepository){
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
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
