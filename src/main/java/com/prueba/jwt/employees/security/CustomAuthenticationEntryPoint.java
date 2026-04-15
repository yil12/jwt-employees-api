package com.prueba.jwt.employees.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.jwt.employees.exception.ErrorCodes;
import com.prueba.jwt.employees.exception.ErrorMessages;
import com.prueba.jwt.employees.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse error = new ErrorResponse(
                ErrorCodes.UNAUTHORIZED,
                ErrorMessages.UNAUTHORIZED
        );

        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
