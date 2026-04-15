package com.prueba.jwt.employees.controller;

import com.prueba.jwt.employees.dto.EmployeeCreateDTO;
import com.prueba.jwt.employees.dto.EmployeeResponseDTO;
import com.prueba.jwt.employees.dto.EmployeeUpdateDTO;
import com.prueba.jwt.employees.model.Employee;
import com.prueba.jwt.employees.service.EmployeeService;
import com.prueba.jwt.employees.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/employees")
@Tag(name = "Empleados", description = "API para la gestión de empleados")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // Mapper Entity -> DTO
    private EmployeeResponseDTO toDTO(Employee e) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(e.getId());
        dto.setNombres(e.getNombres());
        dto.setApellidos(e.getApellidos());
        dto.setSexo(e.getSexo());
        dto.setEdad(e.getEdad());
        dto.setEmail(e.getEmail());
        return dto;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> save(
            @Valid @RequestBody EmployeeCreateDTO dto) {

        log.info("Creando empleado con email: {}", dto.getEmail());

        Employee e = new Employee();
        e.setNombres(dto.getNombres());
        e.setApellidos(dto.getApellidos());
        e.setSexo(dto.getSexo());
        e.setEdad(dto.getEdad());
        e.setEmail(dto.getEmail());

        Employee saved = service.save(e);

        log.info("Empleado creado exitosamente con ID: {}", saved.getId());

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Empleado creado correctamente", toDTO(saved))
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getById(@PathVariable Long id) {

        log.info("Consultando empleado con ID: {}", id);

        Employee emp = service.findById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Empleado encontrado", toDTO(emp))
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateDTO dto) {

        log.info("Actualizando empleado con ID: {}", id);

        Employee e = new Employee();
        e.setNombres(dto.getNombres());
        e.setApellidos(dto.getApellidos());
        e.setSexo(dto.getSexo());
        e.setEdad(dto.getEdad());
        e.setEmail(dto.getEmail());

        Employee updated = service.update(id, e);

        log.info("Empleado actualizado correctamente con ID: {}", id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Empleado actualizado correctamente", toDTO(updated))
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {

        log.warn("Eliminando empleado con ID: {}", id);

        service.delete(id);

        log.warn("Empleado eliminado con ID: {}", id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Empleado eliminado correctamente", null)
        );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAll() {

        log.info("Consultando lista de empleados (máx 30)");

        List<EmployeeResponseDTO> list = service.findAll()
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de empleados", list)
        );
    }


    @GetMapping("/age/40")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getOlder() {

        log.info("Consultando empleados con edad >= 40");

        List<EmployeeResponseDTO> list = service.findByAgeGreaterThanEqual(40)
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Empleados mayores o iguales a 40", list)
        );
    }


    @GetMapping("/female")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getFemale() {

        log.info("Consultando empleados de sexo femenino");

        List<EmployeeResponseDTO> list = service.findBySexo("F")
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Empleados de género femenino", list)
        );
    }
}