package com.talentotech;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.talentotech.excepciones.*;

public class ProductoService {
    private List<Producto> productos = new ArrayList<>();

    public void agregarArmazon(Scanner ingreso){
        System.out.println("Nombre: ");
        String nombre = ingreso.nextLine();

        Double precio;
        int stock;
        Double anchoFrente, largoPatilla, anchoCristal, altoCristal;

        try {
            System.out.println("Precio: ");
            precio = ingreso.nextDouble();

            System.out.println("Stock: ");
            stock = ingreso.nextInt();

            System.out.println("Ancho de Frente: ");
            anchoFrente = ingreso.nextDouble();

            System.out.println("Largo de Patilla: ");
            largoPatilla = ingreso.nextDouble();

            System.out.println("Ancho de Cristal: ");
            anchoCristal = ingreso.nextDouble();

            System.out.println("Alto de Cristal: ");
            altoCristal = ingreso.nextDouble();

            // ingreso.nextLine();
        } catch (InputMismatchException e) {
            ingreso.nextLine();
            throw new EntradaInvalidaExcepcion("Ingrese valores numéricos válidos");
        }
        Medidas medidasP = new Medidas(anchoFrente, largoPatilla, anchoCristal, altoCristal);
        ingreso.nextLine();
        
        Producto nuevoArmazon = new Armazones(nombre, precio, stock, medidasP);
        nuevoArmazon.mostrarDatos();

        boolean respuestaValida = false;
        while (!respuestaValida) {
            System.out.println("Desea guardar este producto? [Si/No] ");
            String respuesta = ingreso.nextLine();
            if (respuesta != null && !respuesta.isEmpty() && respuesta.toLowerCase().substring(0, 1).equals("s")){
                productos.add(nuevoArmazon);
                System.out.println("Carga completada");
                respuestaValida = true;
            } else if (respuesta != null && !respuesta.isEmpty() && respuesta.toLowerCase().substring(0, 1).equals("n")){
                System.out.println("Carga cancelada");
                respuestaValida = true;
            } else {
                System.out.println("Ingrese Si/No para respuesta");
            }
        }
    } 

    public void mostrarTabla(List<Producto> lista){
        if (lista.isEmpty()) {
            System.out.println("Todavia no hay productos cargados");
            return;
        }
        System.out.println(Main.CYAN + "====================================================" + Main.RESET);
        System.out.printf(Main.NEGRITA + "%-5s %-20s %-10s %-10s%n" + Main.RESET, "ID", "Nombre", "Precio", "Stock");
        System.out.println(Main.CYAN + "====================================================" + Main.RESET);

        for (Producto p : lista) {
        System.out.printf("%-5d %-20s %-10.2f %-10d%n", p.getId(), p.getName(), p.getPrice(), p.getStock());
        System.out.println("----------------------------------------------------");
        }
        System.out.println(Main.CYAN + "====================================================" + Main.RESET);
    }

    public void listarProductos(){
      if (productos.isEmpty()){
        throw new ProductoNoEncontradoExcepcion("Todavia no hay productos cargados");
      }
      mostrarTabla(productos);
    }

    public Producto buscarPorID(Scanner ingreso){
        if (productos.isEmpty()){
            throw new ProductoNoEncontradoExcepcion("Todavia no hay productos cargados");
        } 
        listarProductos();
        System.out.println("Que ID desea seleccionar?: ");
        Long idBuscado;
        try {
            idBuscado = ingreso.nextLong();
            ingreso.nextLine();
        } catch (InputMismatchException e){
            ingreso.nextLine();
            throw new EntradaInvalidaExcepcion("ID incorrecto. Ingrese un ID valido");
        }
        
        for (Producto p : productos) {
            if (p.getId().equals(idBuscado)){
                return p;
            }
        }

        throw new ProductoNoEncontradoExcepcion("No se encontró el producto ID: " + idBuscado);
    }


    public List<Producto> buscarPorNombre(Scanner ingreso){
        listarProductos();
        System.out.println("Que nombre desea buscar?: ");
        String nombreBuscado = ingreso.nextLine();
        System.out.println("DEBUG: nombreBuscado = [" + nombreBuscado + "]");
        List<Producto> productosEncontrados = new ArrayList<>();

        if (nombreBuscado != null && !nombreBuscado.isEmpty()){
            for (Producto p : productos) {
                if (p.getName().toLowerCase().contains(nombreBuscado.toLowerCase())){
                    productosEncontrados.add(p);
                }
            }
        }
        return productosEncontrados;
    }

    public void eliminarProducto(Scanner ingreso){
        // listarProductos();
        
        System.out.println("Elija el ID del producto que desea eliminar.");
        
        Producto productoEliminar = buscarPorID(ingreso);
        productoEliminar.mostrarDatos();

        boolean respuestaValida = false;
        while (!respuestaValida) {
            System.out.println("Desea eliminar este producto? [Si/No] ");
            String respuesta = ingreso.nextLine();
            if (respuesta != null && !respuesta.isEmpty() &&respuesta.toLowerCase().substring(0, 1).equals("s")){
                productos.remove(productoEliminar);
                System.out.println("Producto borrado");
                respuestaValida = true;
            } else if (respuesta != null && !respuesta.isEmpty() && respuesta.toLowerCase().substring(0, 1).equals("n")){
                System.out.println("Eliminacion cancelada");
                respuestaValida = true;
            } else {
                System.out.println("Ingrese Si/No para respuesta");
            }
        }
    }

    private void actualizarPrecio(Producto producto, Scanner ingreso){
        System.out.println("Precio actual: " + producto.getPrice());
        System.out.println("Nuevo precio: ");
        try{
            Double nuevoPrecio = ingreso.nextDouble();
            producto.setPrice(nuevoPrecio);
        }
        catch (InputMismatchException e){
            ingreso.nextLine();
            throw new EntradaInvalidaExcepcion("Ingrese un precio valido");
        }
    }

    private void actualizarStock(Producto producto, Scanner ingreso){
        System.out.println("Stock actual: " + producto.getStock());
        System.out.println("Nuevo stock: ");
        try{
            int nuevo = ingreso.nextInt();
            producto.setStock(nuevo);
        }
        catch (InputMismatchException e){
            ingreso.nextLine();
            throw new EntradaInvalidaExcepcion("Ingrese un numero valido");
        }
    }

    public void actualizarProductos(Scanner ingreso){
    
        listarProductos();
        System.out.println("Elija el ID del producto que desea actualizar.");
        Producto productoActualizar = buscarPorID(ingreso);
        int opcion;
        do {
            System.out.println("1) Actualizar precio");
            System.out.println("2) Actualizar stock");
            System.out.println("3) Actualizar precio y stock");
            System.out.println("4) Cancelar");
            System.out.print("Elija una opción: ");

            opcion = ingreso.nextInt();
            ingreso.nextLine();

            switch (opcion) {
                case 1:
                    actualizarPrecio(productoActualizar, ingreso);            
                    break;
                case 2:
                    actualizarStock(productoActualizar, ingreso);
                    break;
                case 3:
                    actualizarPrecio(productoActualizar, ingreso);
                    actualizarStock(productoActualizar, ingreso);
                    break;
                case 4:
                    System.out.println("Datos actualizados.");
                    break;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
                    break;
            }
        } while (opcion != 4);
    }

    public void cargarProductosPrueba() {
        productos.add(new Armazones("RayBan Aviator", 45000.0, 10, new Medidas(140.0, 145.0, 50.0, 35.0)));
        productos.add(new Armazones("Seven", 15000.0, 20, new Medidas(138.0, 142.0, 48.0, 34.0)));
        productos.add(new Armazones("Sol Bordo", 25000.0, 15, new Medidas(140.0, 145.0, 53.0, 48.0)));
    }

    public List<Producto> getProductos(){
        return productos;
    }
}
