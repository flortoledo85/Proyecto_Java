package com.talentotech.optica_backend.model;

import javax.annotation.processing.Generated;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "usuario")
public class Usuario {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre_usuario", unique = true, nullable = false)
    private String nombreUsuario;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "rol", nullable = false)
    private String rol = "USER";

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    protected Usuario() {}

    public Usuario(String nombreUsuario, String email,
                   String passwordHash, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.activo = true;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
