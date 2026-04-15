package com.prueba.jwt.employees.repository;

import com.prueba.jwt.employees.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findTop30By();

    List<Employee> findByEdadGreaterThanEqual(int edad);

    List<Employee> findBySexo(String sexo);

    Optional<Employee> findByEmail(String email);
}