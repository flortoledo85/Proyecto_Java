package com.talentotech.optica_backend.model;
import java.math.BigDecimal;
import java.lang.annotation.Inherited;
import javax.annotation.processing.Generated;
import com.talentotech.optica_backend.exceptions.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private BigDecimal precio;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Producto(String nombre, BigDecimal precio, int stock) {

        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
        totalProductos++;
    }

    protected Producto() {}

    public Categoria getCategoria(){
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
    
    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        if (precio == null || precio.signum() < 0){
            throw new EntradaInvalidaExcepcion("Ingreso un numero valido.");
        }
        this.precio = precio;
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

    public static BigDecimal calcularImpuesto(BigDecimal precio) {
        return precio.multiply(new BigDecimal("0.21")); 
    }

    public static BigDecimal calcularDescuento(BigDecimal precio) {
        return precio.multiply(new BigDecimal("0.10"));
    }

    public void mostrarDatos() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Precio: " + precio);
        System.out.println("Stock: " + stock);
        System.out.println("Total de productos: " + getTotalProductos());
    }
}
