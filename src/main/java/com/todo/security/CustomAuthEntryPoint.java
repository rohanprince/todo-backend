package com.todo.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("status", 401);
        errorBody.put("error", "Unauthorized");
        errorBody.put("message",
                "Unauthorized user. Please put the token. " +
                "Add 'Authorization: Bearer <your_token>' in the request header.");
        errorBody.put("path", request.getRequestURI());
        errorBody.put("timestamp", LocalDateTime.now().toString());

        objectMapper.writeValue(response.getOutputStream(), errorBody);
    }
}
