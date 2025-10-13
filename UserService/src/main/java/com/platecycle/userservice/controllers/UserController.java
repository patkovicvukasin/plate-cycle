package com.platecycle.userservice.controllers;

import com.platecycle.userservice.dto.LoginRequest;
import com.platecycle.userservice.dto.RegisterRequest;
import com.platecycle.userservice.dto.ResetPasswordRequest;
import com.platecycle.userservice.dto.UserResponse;
import com.platecycle.userservice.model.User;
import com.platecycle.userservice.services.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User savedUser = userService.registerUser(registerRequest);
        UserResponse response = userService.mapToResponse(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        UserResponse response = userService.mapToResponse(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate-credentials")
    public ResponseEntity<?> validateCredentials(
            @RequestParam String username,
            @RequestParam String password
    ) {
        boolean ok = userService.checkCredentials(username, password);
        if (ok) {
            return ResponseEntity.ok("VALID");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INVALID");
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<User> resetPassword(@PathVariable Long id,
                                              @RequestBody ResetPasswordRequest request) {
        User updatedUser = userService.resetPassword(id, request.getNewPassword());
        return ResponseEntity.ok(updatedUser);
    }
}
