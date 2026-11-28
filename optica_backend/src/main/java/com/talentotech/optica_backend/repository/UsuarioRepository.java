package com.talentotech.optica_backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.talentotech.optica_backend.model.Usuario;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
}