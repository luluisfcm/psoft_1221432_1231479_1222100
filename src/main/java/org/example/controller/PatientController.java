package org.example.controller;

import jakarta.validation.Valid;
import org.example.ApplicationData;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.auth.api.RegisterPatientRequest;
import org.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/patients")
public class PatientController {

    private final UserRepository userRepository;

    public PatientController() {
        this.userRepository = ApplicationData.userRepository;
    }

    // 🟢 WP#2A — Registo anónimo de paciente
    @PostMapping("/register")
    public ResponseEntity<?> registerAnonymous(@RequestBody @Valid RegisterPatientRequest request) {
        User patient = new User();
        patient.setId(UUID.randomUUID());
        patient.setUsername(request.getEmail());
        patient.setPassword(""); // pode ser vazio ou gerado
        patient.setRole(Role.PATIENT);
        userRepository.save(patient);
        return ResponseEntity.ok().body("Patient registered with ID: " + patient.getId());
    }

    // 🔐 Registo autenticado (exemplo anterior)
    @PostMapping("/patients")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getRole() != null && !user.getRole().name().equals("PATIENT")) {
            return ResponseEntity.badRequest().body("Only role PATIENT is allowed here.");
        }
        user.setId(UUID.randomUUID());
        userRepository.save(user);
        return ResponseEntity.ok("Patient registered: " + user.getUsername());
    }

    // 🔐 Obter dados do paciente autenticado
    @GetMapping("/patients/me")
    public ResponseEntity<User> getLoggedPatient(Authentication auth) {
        Optional<User> user = userRepository.findByUsername(auth.getName());
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
