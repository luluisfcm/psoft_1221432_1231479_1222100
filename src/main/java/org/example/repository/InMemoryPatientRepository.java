package org.example.repository;

import org.example.domain.Patient;

import java.util.*;

public class InMemoryPatientRepository implements PatientRepository {

    private final Map<UUID, Patient> storage = new HashMap<>();

    @Override
    public void save(Patient patient) {
        storage.put(patient.getId(), patient);
    }

    @Override
    public Optional<Patient> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(storage.values());
    }
}
