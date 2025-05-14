package org.example.controller;

import org.example.domain.PasswordPolicy;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController() {
        this.userRepository = new org.example.repository.InMemoryUserRepository(); // Usa a versão in-memory
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        if (!PasswordPolicy.isValid(user.getPassword())) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters long.");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User created: " + user.getUsername());
    }
}
