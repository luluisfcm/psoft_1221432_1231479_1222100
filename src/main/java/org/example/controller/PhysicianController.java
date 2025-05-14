package org.example.controller;

import org.example.domain.Role;
import org.example.ApplicationData;
import org.example.domain.User;
import org.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/physicians")
public class PhysicianController {

    private final UserRepository userRepository;

    public PhysicianController() {
        this.userRepository = ApplicationData.userRepository;

    }

    // Registar médico
    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getRole() != Role.PHYSICIAN) {
            return ResponseEntity.badRequest().body("Only role PHYSICIAN is allowed here.");
        }
        user.setId(UUID.randomUUID());
        userRepository.save(user);
        return ResponseEntity.ok("Physician registered: " + user.getUsername());
    }

    // Listar todos os médicos
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAllByRole("PHYSICIAN"));
    }

    // Ver médico autenticado
    @GetMapping("/me")
    public ResponseEntity<User> getLoggedPhysician(Authentication auth) {
        Optional<User> user = userRepository.findByUsername(auth.getName());
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
