package com.platecycle.userservice.services;

import com.platecycle.userservice.dto.RegisterRequest;
import com.platecycle.userservice.dto.UserResponse;
import com.platecycle.userservice.exceptions.DuplicateEmailException;
import com.platecycle.userservice.exceptions.InvalidCredentialsException;
import com.platecycle.userservice.exceptions.UserNotFoundException;
import com.platecycle.userservice.model.User;
import com.platecycle.userservice.model.UserType;
import com.platecycle.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(RegisterRequest registerRequest) {
        // Provera da li email ili username već postoje
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateEmailException("Email taken: " + registerRequest.getEmail());
        }
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username already in use: " + registerRequest.getUsername());
        }

        User user = new User();

        // Mapiramo podatke iz DTO-ja u entitet
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());  // U produkciji uvek enkriptuj lozinku!
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());

        // Provera korisničkog tipa – ako je ADMIN, registracija se ne dozvoljava
        UserType requestedType = UserType.valueOf(registerRequest.getUserType().toUpperCase());
        if (requestedType == UserType.ADMIN) {
            throw new IllegalArgumentException("Registration as ADMIN is not allowed.");
        }
        user.setUserType(requestedType);

        // Postavljanje ostalih polja
        user.setId(UUID.randomUUID());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        return userRepository.save(user);
    }


    public User loginUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid credentials for: " + username);
        }
        return user;
    }

    // Metoda za mapiranje entiteta u DTO
    public UserResponse mapToResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId().toString());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserType(user.getUserType().name());
        return dto;
    }

    public boolean checkCredentials(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return false;
        return user.getPassword().equals(password);
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public User resetPassword(UUID userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        user.setPassword(newPassword);
        user.setUpdatedAt(Instant.now());
        return userRepository.save(user);
    }
}
