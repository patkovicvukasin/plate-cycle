package com.platecycle.apigateway.controllers;

import com.platecycle.apigateway.clients.UserClient;
import com.platecycle.apigateway.clients.UserClient.LoginRequest;
import com.platecycle.apigateway.clients.UserClient.RegisterRequest;
import com.platecycle.apigateway.clients.UserClient.UserResponse;
import com.platecycle.apigateway.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            UserResponse response = userClient.login(loginRequest);
            if (response != null && response.getUsername() != null) {
                String token = jwtUtil.generateToken(response.getUsername());
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Neispravni kredencijali");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Neispravni kredencijali");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            UserResponse response = userClient.register(registerRequest);
            if (response != null && response.getUsername() != null) {
                String token = jwtUtil.generateToken(response.getUsername());
                return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registracija neuspešna");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Greška pri registraciji: " + e.getMessage());
        }
    }

    static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}
