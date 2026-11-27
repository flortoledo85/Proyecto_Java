package com.talentotech.optica_backend.dto;

import java.util.List;

public record DatosPedido (
    String usuario,
    String email,
    List<DatosLineaPedidos> lineas
) {}
