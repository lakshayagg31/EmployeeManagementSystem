package com.company.departments.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DepartmentServiceFilter extends OncePerRequestFilter {

    @Value("${apigateway.shared.secret}")
    String _SharedSecret;

    @Value("${departmentservice.auth.username}")
    String _DepartmentUsername;

    @Value("${departmentservice.auth.password}")
    String _DepartmentPassword;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
                System.out.println("Headers received:");
                Collections.list(request.getHeaderNames()).forEach(name -> 
                    System.out.println(name + ": " + request.getHeader(name))
                );
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    return;
                }

        // 1. Check API Gateway shared secret 
        final String secret = request.getHeader("X-API-GATEWAY-SECRET");
        System.out.println("X-API-GATEWAY-SECRET header: " + secret);
        if (!_SharedSecret.equals(secret)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Shared secret mismatch.");
            return;
        }

        // 2. Manual Basic Auth check
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Missing basic auth header.");
            return;
        }
        String base64Credentials = authHeader.substring("Basic ".length()).trim();
        byte[] decodedBytes;
        try {
            decodedBytes = Base64.getDecoder().decode(base64Credentials);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid base64 encoding.");
            return;
        }
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] parts = decodedString.split(":", 2);
        String username = parts[0];
        String password = (parts.length > 1) ? parts[1] : "";

        if (!username.equals(_DepartmentUsername) || !password.equals(_DepartmentPassword)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Bad department credentials.");
            return;
        }

        // If both secret and basic auth are valid, allow request to go through
        filterChain.doFilter(request, response);
    }
}
