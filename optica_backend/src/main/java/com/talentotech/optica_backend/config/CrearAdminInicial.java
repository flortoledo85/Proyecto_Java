package com.talentotech.optica_backend.config;

import com.talentotech.optica_backend.model.Usuario;
import com.talentotech.optica_backend.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@ConditionalOnProperty(
    name = "OPTICA_BOOTSTRAP_ADMIN",
    havingValue = "true"
)
public class CrearAdminInicial implements CommandLineRunner {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${OPTICA_ADMIN_USER}")
    private String usuario;

    @Value("${OPTICA_ADMIN_EMAIL}")
    private String email;

    @Value("${OPTICA_ADMIN_PASSWORD}")
    private String clave;

    public CrearAdminInicial(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (repository.findByNombreUsuario(usuario).isPresent()) {
            System.out.println("El administrador ya existe.");
            return;
        }

        String hash = passwordEncoder.encode(clave);

        Usuario admin = new Usuario(
            usuario,
            email,
            hash,
            "ADMIN"
        );

        repository.save(admin);

        System.out.println("Administrador creado correctamente.");
    }
}