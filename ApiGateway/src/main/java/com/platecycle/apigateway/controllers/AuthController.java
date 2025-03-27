package com.platecycle.apigateway.controllers;

import com.platecycle.apigateway.clients.UserClient;
import com.platecycle.apigateway.clients.UserClient.LoginRequest;
import com.platecycle.apigateway.clients.UserClient.RegisterRequest;
import com.platecycle.apigateway.clients.UserClient.UserResponse;
import com.platecycle.apigateway.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserClient userClient;

    public AuthController(JwtUtil jwtUtil, UserClient userClient) {
        this.jwtUtil = jwtUtil;
        this.userClient = userClient;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Prosleđujemo zahtev user servisu za login
            UserResponse response = userClient.login(loginRequest);
            if (response != null && response.getUsername() != null) {
                // Ako je login uspešan, generišemo JWT token
                String token = jwtUtil.generateToken(response.getUsername());
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Neispravni kredencijali");
            }
        } catch (Exception e) {
            e.printStackTrace(); // ili log.error("Login error", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Neispravni kredencijali");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Prosleđujemo zahtev user servisu za registraciju
            UserResponse response = userClient.register(registerRequest);
            if (response != null && response.getUsername() != null) {
                // Nakon uspešne registracije, generišemo JWT token
                String token = jwtUtil.generateToken(response.getUsername());
                return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registracija neuspešna");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Greška pri registraciji: " + e.getMessage());
        }
    }

    // DTO klasa za odgovor sa tokenom
    static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}
