
package com.talentotech.optica_backend.controller;

import java.time.Instant;
import java.util.List;

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
    private final String adminUsuario;
    private final String adminPasswordHash;

    public AuthController(
            JwtEncoder jwtEncoder,
            PasswordEncoder passwordEncoder,
            @Value("${OPTICA_ADMIN_USER}") String adminUsuario,
            @Value("${OPTICA_ADMIN_PASSWORD}") String adminPassword) {

        if (adminUsuario.isBlank() || adminPassword.isBlank()) {
            throw new IllegalArgumentException(
                "Faltan las credenciales del administrador"
            );
        }

        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.adminUsuario = adminUsuario;
        this.adminPasswordHash = passwordEncoder.encode(adminPassword);
    }

    public record DatosLogin(String usuario, String clave) {}

    public record RespuestaLogin(String token, String tipo) {}

    @PostMapping("/login")
    public ResponseEntity<RespuestaLogin> login(
            @RequestBody DatosLogin datos) {

        if (datos == null
                || !adminUsuario.equals(datos.usuario())
                || datos.clave() == null
                || !passwordEncoder.matches(
                    datos.clave(), adminPasswordHash)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Instant ahora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("optica-backend")
            .subject(adminUsuario)
            .issuedAt(ahora)
            .expiresAt(ahora.plusSeconds(3600))
            .claim("roles", List.of("ADMIN"))
            .build();

        String token = jwtEncoder
            .encode(JwtEncoderParameters.from(claims))
            .getTokenValue();

        return ResponseEntity.ok(new RespuestaLogin(token, "Bearer"));
    }
}
