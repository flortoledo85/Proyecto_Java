package com.talentotech;

import java.util.Scanner;

import com.talentotech.excepciones.EntradaInvalidaExcepcion;
import com.talentotech.excepciones.ProductoNoEncontradoExcepcion;
import com.talentotech.excepciones.StockInsuficienteExcepcion;

import java.util.List;
// import java.util.ArrayList;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String NEGRITA = "\u001B[1m";

    public static void main(String[] args) {
        ProductoService productoService = new ProductoService();
        Scanner ingreso = new Scanner(System.in);
        productoService.cargarProductosPrueba();
        PedidoService pedidoService = new PedidoService(productoService);
        int opcion;

        do {
            System.out.println(CYAN + "================================" + RESET);
            System.out.println(CYAN + NEGRITA + "=== SISTEMA DE GESTIÓN - TALENTOTECH ===" + RESET);
            System.out.println(CYAN + "================================" + RESET);
            System.out.println("1) Agregar producto");
            System.out.println("2) Listar productos");
            System.out.println("3) Buscar/Actualizar producto");
            System.out.println("4) Eliminar producto");
            System.out.println("5) Crear un pedido");
            System.out.println("6) Listar pedidos");
            System.out.println("7) Salir");
            System.out.print("Elija una opción: ");

            opcion = ingreso.nextInt();
            ingreso.nextLine();
            try {
                switch (opcion) {
                    case 1:
                        productoService.agregarArmazon(ingreso);
                        break;
                    case 2:
                        productoService.listarProductos();
                        break;
                    case 3:
                        System.out.println("1) Buscar por ID");
                        System.out.println("2) Buscar por Nombre");
                        System.out.println("3) Actualizar producto");
                        System.out.println("Elija una opción.");
                        int subOpcion = ingreso.nextInt();
                        ingreso.nextLine();
                        switch (subOpcion) {
                            case 1:
                                Producto encontrado = productoService.buscarPorID(ingreso);
                                encontrado.mostrarDatos();
                                break;
                            case 2:
                                List<Producto> encontrados = productoService.buscarPorNombre(ingreso);
                                productoService.mostrarTabla(encontrados);
                                break;
                            case 3:
                                productoService.actualizarProductos(ingreso);
                                break;
                            default:
                                System.out.println(Main.VERDE+"Opción invalida."+Main.RESET);
                                break;
                        }
                        break;
                    case 4:
                        productoService.eliminarProducto(ingreso);
                        break;
                    case 5:
                        pedidoService.crearPedido(ingreso);
                        break;
                    case 6:
                        pedidoService.listarPedido();
                        break;
                    case 7:
                        System.out.println(Main.NEGRITA + "Saliendo del sistema...");
                        break;
                    default:
                        System.out.println(Main.VERDE + "Opción inválida, intente de nuevo." + Main.RESET);
                        break;
                }
            }catch (ProductoNoEncontradoExcepcion | EntradaInvalidaExcepcion | StockInsuficienteExcepcion e) {
                System.out.println(ROJO + "Error: " + e.getMessage() + RESET);
            }
        } while (opcion != 7);

        ingreso.close();
    }
}
