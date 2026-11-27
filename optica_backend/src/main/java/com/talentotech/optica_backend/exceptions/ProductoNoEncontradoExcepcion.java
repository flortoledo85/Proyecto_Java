package com.talentotech.optica_backend.exceptions;

public class ProductoNoEncontradoExcepcion extends RuntimeException{
    public ProductoNoEncontradoExcepcion(String mensaje){
        super(mensaje);
    }
}