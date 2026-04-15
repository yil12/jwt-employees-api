package com.prueba.jwt.employees.dto;

import jakarta.validation.constraints.*;

public class EmployeeUpdateDTO {

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @Pattern(regexp = "M|F")
    private String sexo;

    @Min(18)
    private int edad;

    @Email
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
