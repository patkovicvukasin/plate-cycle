package com.platecycle.apigateway.clients;

import com.platecycle.apigateway.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Ovaj Feign klijent poziva user servis koji radi na portu 8081
@FeignClient(name = "UserService", url = "http://host.docker.internal:8081", configuration = FeignConfig.class)

public interface UserClient {

    @PostMapping(value = "/api/users/login", consumes = "application/json", produces = "application/json")
    UserResponse login(@RequestBody LoginRequest loginRequest);

    @PostMapping(value = "/api/users/register", consumes = "application/json", produces = "application/json")
    UserResponse register(@RequestBody RegisterRequest registerRequest);

    // DTO za login – struktura mora odgovarati DTO-ju u user servisu
    class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // DTO za registraciju – struktura mora odgovarati DTO-ju u user servisu
    class RegisterRequest {
        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String userType;

        public RegisterRequest() {}

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getUserType() { return userType; }
        public void setUserType(String userType) { this.userType = userType; }
    }

    // DTO za odgovor – struktura mora odgovarati DTO-ju u user servisu
    class UserResponse {
        private String id;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private String userType;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getUserType() { return userType; }
        public void setUserType(String userType) { this.userType = userType; }
    }
}
