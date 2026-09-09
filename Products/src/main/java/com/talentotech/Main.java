package com.talentotech;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Producto p1 = new Producto("Coca", 2500., 100);
        // Producto p2 = new Producto("Seven", 1500., 120);

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

        Producto p1 = new Producto (nombre,precio,100);

        p1.mostrarDatos();

        ingreso.close();
    } 
}
