package com.talentotech.excepciones;

public class StockInsuficienteExcepcion extends RuntimeException {
    public StockInsuficienteExcepcion(String mensaje){
        super(mensaje);
    }
}
