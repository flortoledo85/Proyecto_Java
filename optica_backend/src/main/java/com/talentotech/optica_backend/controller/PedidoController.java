
package com.talentotech.optica_backend.controller;

import com.talentotech.optica_backend.dto.DatosPedido;
import com.talentotech.optica_backend.dto.RespuestaPedido;
import com.talentotech.optica_backend.model.Pedido;
import com.talentotech.optica_backend.model.Usuario;
import com.talentotech.optica_backend.repository.PedidoRepository;
import com.talentotech.optica_backend.repository.UsuarioRepository;
import com.talentotech.optica_backend.service.PedidoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoController(
            PedidoService pedidoService,
            PedidoRepository pedidoRepository,
            UsuarioRepository usuarioRepository) {

        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaPedido crearPedido(
            @RequestBody DatosPedido datos,
            @AuthenticationPrincipal Jwt jwt) {

        Usuario usuario = obtenerUsuario(jwt);

        // Usamos los datos de la cuenta autenticada.
        // No confiamos en el nombre o email enviados por el frontend.
        DatosPedido datosSeguros = new DatosPedido(
            usuario.getNombreUsuario(),
            usuario.getEmail(),
            datos.lineas()
        );

        Pedido pedido = pedidoService.crearPedido(datosSeguros);

        return new RespuestaPedido(
            pedido.getId(),
            pedido.getTotal()
        );
    }

    @GetMapping("/mis")
    public List<PedidoVista> misPedidos(
            @AuthenticationPrincipal Jwt jwt) {

        Usuario usuario = obtenerUsuario(jwt);

        return pedidoRepository
            .findByUsuarioOrderByFechaDesc(usuario.getNombreUsuario())
            .stream()
            .map(p -> new PedidoVista(
                p.getId(),
                p.getFecha(),
                p.getTotal(),
                p.getLineas().stream()
                    .map(l -> new LineaVista(
                        l.getProducto().getNombre(),
                        l.getCantidad(),
                        l.getSubtotal()
                    ))
                    .toList()
            ))
            .toList();
    }

    private Usuario obtenerUsuario(Jwt jwt) {

        if (jwt == null) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED
            );
        }

        Usuario usuario = usuarioRepository
            .findByNombreUsuario(jwt.getSubject())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED
            ));

        if (!usuario.isActivo()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN
            );
        }

        return usuario;
    }

    public record LineaVista(
        String producto,
        int cantidad,
        BigDecimal subtotal
    ) {}

    public record PedidoVista(
        Integer id,
        LocalDateTime fecha,
        BigDecimal total,
        List<LineaVista> lineas
    ) {}
}
