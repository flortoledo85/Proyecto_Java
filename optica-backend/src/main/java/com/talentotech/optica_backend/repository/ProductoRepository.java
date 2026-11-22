package com.talentotech.optica_backend.repository;

import com.talentotech.optica_backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
