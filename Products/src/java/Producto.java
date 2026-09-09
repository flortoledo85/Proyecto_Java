package com.talentotech;

public class Producto {
    private static Long contadorId = 0L;
    private static int totalProductos = 0;
    private Long id;
    private String name;
    private Double price;
    private int stock;

    // public Producto() {
    //     this.id = ++contadorId;
    //     this.name = "Producto X";
    //     this.price = 0.0;
    //     this.stock = 0;
    // }

    // public Producto(String name, Double price, int stock) {
    //     this.id = ++contadorId;
    //     this.name = name;
    //     this.price = price;
    //     this.stock = stock;
    //     if (this.stock == 0) {
    //         this.stock = 1;
    //     } else {
    //         this.stock = stock;
    //     }
    // }
    public Producto(String name, Double price, int stock) {
        this.id = ++contadorId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        totalProductos++;
    }

    public Long getId() {
        return id;
    }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public static int getTotalProductos(){
        return totalProductos;
    }

    public void descontarStock(int cantidad) {
        this.stock -= cantidad;
    }

    public static Double calcularImpuesto(Double price) {
        return price * 0.21; //IVA
    }

    public static Double calcularDescuento(Double price) {
        return price * 0.10;
    }
    
    public void mostrarDatos() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + name);
        System.out.println("Precio: " + price);
        System.out.println("Stock: " + stock);
        System.out.println("Total de productos: " + getTotalProductos());
    }
}
