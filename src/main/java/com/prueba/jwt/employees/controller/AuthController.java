package com.prueba.jwt.employees.controller;

import com.prueba.jwt.employees.dto.AuthResponseDTO;
import com.prueba.jwt.employees.exception.ErrorMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.prueba.jwt.employees.exception.InvalidPasswordException;
import com.prueba.jwt.employees.exception.UserNotFoundException;
import com.prueba.jwt.employees.model.User;
import com.prueba.jwt.employees.repository.UserRepository;
import com.prueba.jwt.employees.response.ApiResponse;
import com.prueba.jwt.employees.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para login y seguridad")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Operation(
            summary = "Login de usuario",
            description = "Permite autenticarse y obtener un token JWT válido"
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(
            @Valid @RequestBody AuthRequest request) {

        log.info("Intento de login para usuario: {}", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado: {}", request.getUsername());
                    return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Contraseña incorrecta para usuario: {}", request.getUsername());
            throw new InvalidPasswordException(ErrorMessages.INVALID_PASSWORD);
        }

        String token = jwtService.generateToken(user.getUsername());

        log.info("Login exitoso para usuario: {}", user.getUsername());

        AuthResponseDTO responseDTO = new AuthResponseDTO(token);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Autenticación exitosa", responseDTO)
        );
    }
}