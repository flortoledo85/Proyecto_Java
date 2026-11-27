package com.talentotech.optica_backend.controller;

import com.talentotech.optica_backend.dto.DatosPedido;
import com.talentotech.optica_backend.service.PedidoService;
import com.talentotech.optica_backend.model.Pedido;
import com.talentotech.optica_backend.dto.RespuestaPedido;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaPedido crearPedido(@RequestBody DatosPedido datos){
        Pedido pedido = pedidoService.crearPedido(datos);

        return new RespuestaPedido(
            pedido.getId(),
            pedido.getTotal()
        );
    }
}
