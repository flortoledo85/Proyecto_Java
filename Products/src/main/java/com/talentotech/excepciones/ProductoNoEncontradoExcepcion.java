package com.talentotech.excepciones;

public class ProductoNoEncontradoExcepcion extends RuntimeException{
    public ProductoNoEncontradoExcepcion(String mensaje){
        super(mensaje);
    }
}