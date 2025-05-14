package org.example.controller;

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
@RequestMapping("/patients")
public class PatientController {

    private final UserRepository userRepository;

    public PatientController() {
        this.userRepository = ApplicationData.userRepository;
    }

    // Criar novo paciente
    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getRole() != null && !user.getRole().name().equals("PATIENT")) {
            return ResponseEntity.badRequest().body("Only role PATIENT is allowed here.");
        }
        user.setId(UUID.randomUUID());
        userRepository.save(user);
        return ResponseEntity.ok("Patient registered: " + user.getUsername());
    }

    // Listar todos os pacientes
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAllByRole("PATIENT"));
    }

    // Obter dados do paciente autenticado
    @GetMapping("/me")
    public ResponseEntity<User> getLoggedPatient(Authentication auth) {
        Optional<User> user = userRepository.findByUsername(auth.getName());
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
