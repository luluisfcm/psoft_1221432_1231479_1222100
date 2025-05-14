package org.example.controller;

import org.example.ApplicationData;
import org.example.domain.Appointment;
import org.example.repository.InMemoryAppointmentRepository;
import org.example.repository.InMemoryUserRepository;
import org.example.repository.UserRepository;
import org.example.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;
    private final UserRepository userRepository;

    public AppointmentController() {
        this.userRepository = ApplicationData.userRepository;
        this.service = new AppointmentService(new InMemoryAppointmentRepository());
    }

    @PostMapping
    public ResponseEntity<?> schedule(@RequestBody Map<String, String> body) {
        String patientUsername = body.get("patientUsername");
        String physicianUsername = body.get("physicianUsername");

        UUID patientId = userRepository.findByUsername(patientUsername)
                .orElseThrow(() -> new RuntimeException("Patient not found"))
                .getId();

        UUID physicianId = userRepository.findByUsername(physicianUsername)
                .orElseThrow(() -> new RuntimeException("Physician not found"))
                .getId();

        Appointment appt = service.schedule(
                patientId,
                physicianId,
                body.get("date"),
                body.get("time"),
                body.get("consultationType")
        );

        return ResponseEntity.status(201).body(appt);
    }



    @GetMapping("/my")
    public ResponseEntity<List<Appointment>> myAppointments(Authentication auth) {
        String username = auth.getName();

        UUID patientId = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"))
                .getId();

        return ResponseEntity.ok(service.getByPatientId(patientId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        Appointment updated = service.update(
                id,
                body.get("date"),
                body.get("time"),
                body.get("consultationType")
        );
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable UUID id) {
        service.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getDetails(@PathVariable UUID id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
