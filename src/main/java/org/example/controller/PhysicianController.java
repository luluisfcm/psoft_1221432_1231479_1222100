package org.example.controller;

import org.example.domain.Physician;
import org.example.service.PhysicianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/physicians")
public class PhysicianController {

    private final PhysicianService service;

    public PhysicianController(PhysicianService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Physician> createPhysician(@RequestBody Physician physician) {
        Physician saved = service.save(physician);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Physician> getPhysicianById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Physician>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Physician>> searchPhysicians(@RequestParam String keyword) {
        List<Physician> result = service.searchByNameOrSpecialty(keyword);
        return ResponseEntity.ok(result);
    }

}
