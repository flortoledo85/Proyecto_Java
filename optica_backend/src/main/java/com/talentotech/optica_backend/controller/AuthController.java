
package com.talentotech.optica_backend.controller;

import java.time.Instant;
import java.util.List;

import com.talentotech.optica_backend.model.Usuario;
import com.talentotech.optica_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public AuthController(
            JwtEncoder jwtEncoder,
            PasswordEncoder passwordEncoder,
            UsuarioRepository usuarioRepository) {


        this.jwtEncoder = jwtEncoder; 
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    public record DatosLogin(String usuario, String clave) {}

    public record RespuestaLogin(String token, String tipo) {}

    public record DatosRegistro(
        String nombreUsuario,
        String email,
        String clave
    ) {}

    public record RespuestaRegistro(
        Integer id,
        String nombreUsuario,
        String email
    ) {}

    @PostMapping("/login")
    public ResponseEntity<RespuestaLogin> login(
            @RequestBody DatosLogin datos) {

        if (datos == null || datos.usuario() == null || datos.clave() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = usuarioRepository
            .findByNombreUsuario(datos.usuario())
            .orElse(null);

        if (usuario == null || !usuario.isActivo()
                || !passwordEncoder.matches(
                    datos.clave(), usuario.getPasswordHash())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Instant ahora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("optica_backend")
            .subject(usuario.getNombreUsuario())
            .issuedAt(ahora)
            .expiresAt(ahora.plusSeconds(3600))
            .claim("roles", List.of(usuario.getRol()))
            .build();

        String token = jwtEncoder
            .encode(JwtEncoderParameters.from(claims))
            .getTokenValue();

        return ResponseEntity.ok(new RespuestaLogin(token, "Bearer"));
    }


    @PostMapping("/registro")
    public ResponseEntity<RespuestaRegistro> registrar(
            @RequestBody DatosRegistro datos) {

        if (datos == null ||
            datos.nombreUsuario() == null ||
            datos.email() == null ||
            datos.clave() == null) {

            return ResponseEntity.badRequest().build();
        }

        String nombre = datos.nombreUsuario().trim();
        String email = datos.email().trim();

        if (nombre.isBlank() || email.isBlank() ||
            datos.clave().length() < 8) {

            return ResponseEntity.badRequest().build();
        }

        if (usuarioRepository.findByNombreUsuario(nombre).isPresent()
                || usuarioRepository.existsByEmail(email)) {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String hash = passwordEncoder.encode(datos.clave());

        Usuario nuevoUsuario = new Usuario(
            nombre,
            email,
            hash,
            "USER"
        );

        Usuario guardado = usuarioRepository.save(nuevoUsuario);

        RespuestaRegistro respuesta = new RespuestaRegistro(
            guardado.getId(),
            guardado.getNombreUsuario(),
            guardado.getEmail()
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(respuesta);
    }

}
