package com.talentotech.excepciones;

public class EntradaInvalidaExcepcion extends RuntimeException {
    public EntradaInvalidaExcepcion(String mensaje){
        super(mensaje);
    }
}
