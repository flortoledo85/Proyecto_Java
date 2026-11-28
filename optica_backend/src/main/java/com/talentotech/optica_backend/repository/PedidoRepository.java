package com.talentotech.optica_backend.repository;

import com.talentotech.optica_backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @EntityGraph(attributePaths = {"lineas", "lineas.producto"})
    List<Pedido> findByUsuarioOrderByFechaDesc(String usuario);
}
