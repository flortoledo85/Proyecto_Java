package com.talentotech;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Producto p1 = new Producto("Coca", 2500., 100);
        Producto p2 = new Armazones("Seven", 1500., 120, 15.5);

        // Producto p3 = new Producto("Pepsi", 2340.0, 130);

        // System.out.println(p1.getName());
        // System.out.println(p1.getId());
        // System.out.println(p3.getPrice());


        Scanner ingreso = new Scanner(System.in);

        // entrada de datos por consola
        System.out.println("Nombre : ");
        String nombre = ingreso.nextLine();

        System.out.println("Precio: ");
        double precio = ingreso.nextDouble();
        ingreso.nextLine(); // limpiar el buffer despues de nextDouble()

        Producto p1 = new Armazones(nombre,precio,100,14.5);
        p1.mostrarDatos();

        System.out.println("-----------------------------------------");

        double precioDescuento = Vendible.calcularDescuento(p1.getPrice(), 15.0);
        System.out.println("Descuento: "+ precioDescuento);
        System.out.println("-----------------------------------------");
        double precioProducto1 = p1.getPrice();
        int precio1 = (int) precioProducto1;
        System.out.println("Precio de producto "+ precio1 +"\t"+ precio);
    
        System.out.println("-----------------------------------------");
        Armazones armazon1 = (Armazones) p2;
        armazon1.aplicarDescuento(20.);
        armazon1.generarEtiqueta();
        System.out.println("-----------------------------------------");

        List<Producto> productos = new ArrayList<>();

        productos.add(p1);
        productos.add(p2);

        productos.add(new Armazones("Rayban", 25000., 200, 14.5));

        Producto p = productos.get(2);
        System.out.println(p.getName());
        System.out.println("-----------------FIN---------------------");
        ingreso.close();
    }
}
