package com.talentotech.optica_backend.dto;

import java.math.BigDecimal;

public record RespuestaPedido(
    Integer id,
    BigDecimal total
) {}