package org.example.service;

import org.example.domain.Physician;
import org.example.repository.PhysicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




@Service
public class PhysicianService {

    private final PhysicianRepository repository;

    public PhysicianService(PhysicianRepository repository) {
        this.repository = repository;
    }

    public Physician save(Physician physician) {
        return repository.save(physician);
    }

    public List<Physician> findAll() {
        return repository.findAll();
    }

    public Optional<Physician> findById(Long id) {
        return repository.findById(id);
    }

    public List<Physician> searchByNameOrSpecialty(String keyword) {
        return repository.findByNameContainingIgnoreCaseOrSpecialtyContainingIgnoreCase(keyword, keyword);
    }

}

