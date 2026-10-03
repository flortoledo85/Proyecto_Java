package com.talentotech;

import java.util.Scanner;

import com.talentotech.excepciones.EntradaInvalidaExcepcion;
import com.talentotech.excepciones.ProductoNoEncontradoExcepcion;
import com.talentotech.excepciones.StockInsuficienteExcepcion;

import java.util.List;
// import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Producto p1 = new Producto("Coca", 2500., 100);
        ProductoService productoService = new ProductoService();
        Scanner ingreso = new Scanner(System.in);
        productoService.cargarProductosPrueba();
        Pedido pedidoCliente = new Pedido(null);
        int opcion;

        do {
            System.out.println("================================");
            System.out.println("=== SISTEMA DE GESTIÓN - TALENTOTECH ===");
            System.out.println("================================");
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
                                System.out.println("Opción invalida.");
                                break;
                        }
                        break;
                    case 4:
                        productoService.eliminarProducto(ingreso);
                        break;
                    case 5:
                        pedidoCliente.agregarProducto(null, subOpcion);
                        break;
                    case 6:
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción inválida, intente de nuevo.");
                        break;
                }
            }catch (ProductoNoEncontradoExcepcion | EntradaInvalidaExcepcion | StockInsuficienteExcepcion e) {
                System.out.println("Error : " + e.getMessage());
            }
        } while (opcion != 7);

        ingreso.close();
    }
}
