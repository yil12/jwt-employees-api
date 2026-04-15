package com.prueba.jwt.employees.dto;

import jakarta.validation.constraints.*;

public class EmployeeCreateDTO {

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "El sexo es obligatorio")
    @Pattern(regexp = "M|F", message = "El sexo debe ser M o F")
    private String sexo;

    @Min(value = 18, message = "La edad mínima es 18")
    private int edad;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    private String email;

    // ===== GETTERS =====

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getSexo() {
        return sexo;
    }

    public int getEdad() {
        return edad;
    }

    public String getEmail() {
        return email;
    }

    // ===== SETTERS =====

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}