package com.talentotech.optica_backend.model;

public interface Vendible {
    String ESTADO_DEFAULT = "Disponible";

    Double aplicarDescuento(Double porcentaje);

    default void mostrarEstado() {
        System.out.println("Estado del producto: " + ESTADO_DEFAULT);
    }

    static Double calcularDescuento(Double precio, Double porcentaje) {
        return precio - (precio*porcentaje/100);
    }

}
