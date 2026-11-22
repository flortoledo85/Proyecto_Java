package com.talentotech.optica_backend.model;
import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;

import com.talentotech.optica_backend.exceptions.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private static int totalProductos = 0;
    private String nombre;
    private Double precio;
    private int stock;
    @Transient
    private String categoria;

    public Producto(String nombre, Double precio, int stock) {

        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
        totalProductos++;
    }

    protected Producto() {}

    public String getCategoria(){
        return categoria;
    };

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()){
            this.nombre = nombre;
        }else {
            throw new EntradaInvalidaExcepcion("Ingrese un nombre valido");
        }
    }

    public String getNombre() {
        return nombre;
    }
    
    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        if (precio >=0){
            this.precio = precio;
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

    public static Double calcularImpuesto(Double precio) {
        return precio * 0.21; //IVA
    }

    public static Double calcularDescuento(Double precio) {
        return precio * 0.10;
    }
    
    public void mostrarDatos() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Precio: " + precio);
        System.out.println("Stock: " + stock);
        System.out.println("Total de productos: " + getTotalProductos());
    }
}
