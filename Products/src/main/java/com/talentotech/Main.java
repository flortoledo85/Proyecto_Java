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
                        break;
                    case 5:
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

        // Medidas medidasRayban = new Medidas(143., 148., 55.,48.);
        // Producto p2 = new Armazones("RayBan", 15000., 120, medidasRayban);

        // // Producto p3 = new Producto("Pepsi", 2340.0, 130);

        // // System.out.println(p1.getName());
        // // System.out.println(p1.getId());
        // // System.out.println(p3.getPrice());


        // // Scanner ingreso = new Scanner(System.in);

        // // entrada de datos por consola
        // System.out.println("Nombre : ");
        // String nombre = ingreso.nextLine();

        // System.out.println("Precio: ");
        // double precio = ingreso.nextDouble();
        // ingreso.nextLine(); // limpiar el buffer despues de nextDouble()

        // System.out.println("Stock: ");
        // int stock = ingreso.nextInt();
        // ingreso.nextLine();

        // System.out.println("Ancho de Frente: ");
        // double anchoFrente = ingreso.nextDouble();

        // System.out.println("Largo de Patilla: ");
        // double largoPatilla = ingreso.nextDouble();

        // System.out.println("Ancho de Cristal: ");
        // double anchoCristal = ingreso.nextDouble();

        // System.out.println("Alto de Cristal: ");
        // double altoCristal = ingreso.nextDouble();

        // Medidas medidasP = new Medidas(anchoFrente, largoPatilla, anchoCristal, altoCristal);

        // Producto p1 = new Armazones(nombre,precio,stock, medidasP);
        // p1.mostrarDatos();

        // System.out.println("-----------------------------------------");

        // double precioDescuento = Vendible.calcularDescuento(p1.getPrice(), 15.0);
        // System.out.println("Descuento: "+ precioDescuento);
        // System.out.println("-----------------------------------------");
        // double precioProducto1 = p1.getPrice();
        // int precio1 = (int) precioProducto1;
        // System.out.println("Precio de producto "+ precio1 +"\t"+ precio);
    
        // System.out.println("-----------------------------------------");
        // Armazones armazon1 = (Armazones) p2;
        // armazon1.aplicarDescuento(20.);
        // armazon1.generarEtiqueta();
        // System.out.println("-----------------------------------------");

        // List<Producto> productos = new ArrayList<>();

        // productos.add(p1);
        // productos.add(p2);

        // productos.add(new Armazones("Sol Bordo", 25000., 200, new Medidas(140.,145.,53.,48.)));

        // Producto p = productos.get(2);
        // System.out.println(p.getName());
        // System.out.println("-----------------FIN---------------------");
        // ingreso.close();
    }
}
