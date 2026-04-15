package com.prueba.jwt.employees.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {

    @NotBlank
    @Schema(description = "Nombre de usuario", example = "username")
    private String username;

    @Schema(description = "Contraseña del usuario", example = "password")
    private String password;

    // getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
