package com.talentotech;

public interface Vendible {
    String ESTADO_DEFAULT = "Disponible";

    void aplicarDescuento(Double porcentaje);

    default void mostrarEstado() {
        System.out.println("Estado del producto: " + ESTADO_DEFAULT);
    }

    static Double calcularDescuento(Double price, Double porcentaje) {
        return price - (price*porcentaje/100);
    }

}
