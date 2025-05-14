package org.example.service;

import org.example.domain.Physician;
import org.example.domain.Specialization;
import org.example.repository.PhysicianRepository;

public class PhysicianRegistrationService {
    private final PhysicianRepository repository;

    public PhysicianRegistrationService(PhysicianRepository repository) {
        this.repository = repository;
    }

    public void register(String name, String contact, String hours, Specialization specialization) {
        Physician physician = new Physician(name, contact, hours, specialization);
        repository.save(physician);
    }
}
