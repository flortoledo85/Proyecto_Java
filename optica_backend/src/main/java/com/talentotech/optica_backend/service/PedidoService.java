package com.talentotech.optica_backend.service;

import java.util.ArrayList;
import java.util.List;

import com.talentotech.optica_backend.exceptions.*;
import com.talentotech.optica_backend.model.Pedido;
import com.talentotech.optica_backend.model.Producto;
import com.talentotech.optica_backend.repository.PedidoRepository;
import com.talentotech.optica_backend.repository.ProductoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.talentotech.optica_backend.dto.DatosPedido;
import com.talentotech.optica_backend.dto.DatosLineaPedidos;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository){
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        }

    @Transactional
    public Pedido crearPedido(DatosPedido datos) {
        String usuario = datos.usuario();
        String email = datos.email();

        if (datos == null || datos.lineas() == null || datos.lineas().isEmpty()) {
            throw new ProductoNoEncontradoExcepcion("El pedido debe contener productos");
        }

        Pedido pedido = new Pedido(datos.usuario(), datos.email());

        for (DatosLineaPedidos linea : datos.lineas()) {
            if (linea == null || linea.productoId() == null || linea.cantidad() <= 0){
                throw new EntradaInvalidaExcepcion("Producto o cantidad invalidas");
            }
            Producto producto = productoRepository.buscarParaCompra(linea.productoId()).orElseThrow(() -> new ProductoNoEncontradoExcepcion("Producto no encontrado: " + linea.productoId()));

            if (producto.getStock() < linea.cantidad()) {
                throw new StockInsuficienteExcepcion("Stock insuficiente: " + producto.getNombre());
            }
        pedido.agregarProducto(producto, linea.cantidad());   
        producto.setStock(producto.getStock() - linea.cantidad());
        productoRepository.save(producto);
        }
        return pedidoRepository.save(pedido);
    }
}
