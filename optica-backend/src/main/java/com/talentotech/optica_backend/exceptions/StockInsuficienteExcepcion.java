package com.talentotech.optica_backend.exceptions;

public class StockInsuficienteExcepcion extends RuntimeException {
    public StockInsuficienteExcepcion(String mensaje){
        super(mensaje);
    }
}
