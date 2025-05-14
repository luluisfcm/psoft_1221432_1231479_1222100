package org.example.controller;

import org.example.domain.Specialization;
import org.example.repository.SpecializationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/specializations")
public class SpecializationController {

    private final SpecializationRepository repository;

    public SpecializationController() {
        this.repository = new org.example.repository.InMemorySpecializationRepository(); // Usa a versão in-memory
    }

    @PostMapping
    public ResponseEntity<Specialization> create(@RequestBody Specialization specialization) {
        UUID id = specialization.getId() == null ? UUID.randomUUID() : specialization.getId();
        Specialization newSpec = new Specialization(specialization.getName(), id);
        repository.save(newSpec);
        return ResponseEntity.ok(newSpec);
    }
}
