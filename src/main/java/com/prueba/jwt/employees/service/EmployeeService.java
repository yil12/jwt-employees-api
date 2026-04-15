package com.prueba.jwt.employees.service;

import com.prueba.jwt.employees.exception.DuplicateResourceException;
import com.prueba.jwt.employees.exception.EmployeeNotFoundException;
import com.prueba.jwt.employees.exception.ErrorMessages;
import com.prueba.jwt.employees.model.Employee;
import com.prueba.jwt.employees.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee save(Employee e) {

        log.info("Validando creación de empleado con email: {}", e.getEmail());

        if (repo.findByEmail(e.getEmail()).isPresent()) {
            log.warn("Intento de registro con email duplicado: {}", e.getEmail());
            throw new DuplicateResourceException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        Employee saved = repo.save(e);

        log.info("Empleado guardado correctamente con ID: {}", saved.getId());

        return saved;
    }

    public List<Employee> findAll() {
        log.info("Consultando máximo 30 empleados");
        return repo.findTop30By();
    }

    public Employee findById(Long id) {
        log.info("Buscando empleado con ID: {}", id);

        return repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Empleado no encontrado con ID: {}", id);
                    return new EmployeeNotFoundException(ErrorMessages.EMPLOYEE_NOT_FOUND + "con ID: " + id);
                });
    }

    public Employee update(Long id, Employee e) {

        log.info("Actualizando empleado con ID: {}", id);

        Employee existing = repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Empleado no encontrado para actualización ID: {}", id);
                    return new EmployeeNotFoundException(ErrorMessages.EMPLOYEE_NOT_FOUND + "con ID: " + id);
                });

        if (e.getEmail() != null &&
                !existing.getEmail().equals(e.getEmail()) &&
                repo.findByEmail(e.getEmail()).isPresent()) {

            log.warn("Intento de actualizar con email duplicado: {}", e.getEmail());
            throw new DuplicateResourceException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        existing.setNombres(e.getNombres());
        existing.setApellidos(e.getApellidos());
        existing.setEdad(e.getEdad());
        existing.setSexo(e.getSexo());

        if (e.getEmail() != null) {
            existing.setEmail(e.getEmail());
        }

        Employee updated = repo.save(existing);

        log.info("Empleado actualizado correctamente con ID: {}", id);

        return updated;
    }

    public void delete(Long id) {

        log.warn("Intentando eliminar empleado con ID: {}", id);

        if (!repo.existsById(id)) {
            log.warn("No se encontró empleado para eliminar con ID: {}", id);
            throw new EmployeeNotFoundException("No se puede eliminar." + ErrorMessages.EMPLOYEE_NOT_FOUND + " con ID: " + id);
        }

        repo.deleteById(id);

        log.warn("Empleado eliminado con ID: {}", id);
    }

    public List<Employee> findByAgeGreaterThanEqual(int edad) {
        log.info("Buscando empleados con edad >= {}", edad);
        return repo.findByEdadGreaterThanEqual(edad);
    }

    public List<Employee> findBySexo(String sexo) {
        log.info("Buscando empleados por sexo: {}", sexo);
        return repo.findBySexo(sexo);
    }
}