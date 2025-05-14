package org.example.repository;

import org.example.domain.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository {
    void save(Patient patient);
    Optional<Patient> findById(UUID id);
    List<Patient> findAll();
}
