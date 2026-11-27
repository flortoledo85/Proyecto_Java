package com.talentotech.optica_backend.model;
import java.math.BigDecimal;

public interface Vendible {
    String ESTADO_DEFAULT = "Disponible";

    BigDecimal aplicarDescuento(BigDecimal porcentaje);

    default void mostrarEstado() {
        System.out.println("Estado del producto: " + ESTADO_DEFAULT);
    }

    static BigDecimal calcularDescuento(BigDecimal precio, BigDecimal porcentaje) {
        BigDecimal importeDescuento = precio.multiply(porcentaje.movePointLeft(2));
        return precio.subtract(importeDescuento);
    }

}
