package com.talentotech;
import com.talentotech.excepciones.*;

public abstract class Producto {
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

        setName(name);
        setPrice(price);
        setStock(stock);
        this.id = ++contadorId;
        // this.name = name;
        // this.price = price;
        // this.stock = stock;
        totalProductos++;
    }

    public abstract String getCategoria();

    public Long getId() {
        return id;
    }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()){
            this.name = name;
        }else {
            throw new EntradaInvalidaExcepcion("Ingrese un nombre valido");
        }
    }

    public String getName() {
        return name;
    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price >=0){
            this.price = price;
        }else{
            throw new EntradaInvalidaExcepcion("Ingreso un numero valido.");
        }
    }

    public int getStock() {
        return stock;   
    }

    public void setStock(int stock) {
        if (stock >=0) {
            this.stock = stock;
        }else {
            throw new EntradaInvalidaExcepcion("Ingrese un numero valido.");
        }
    }

    public static int getTotalProductos(){
        return totalProductos;
    }

    public void descontarStock(int cantidad) {
        if (cantidad >=0 && cantidad <= this.getStock()) {
            this.stock -= cantidad; 
        } else {
            throw new StockInsuficienteExcepcion("La cantidad sobrepasa el stock actual.");
        }
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
