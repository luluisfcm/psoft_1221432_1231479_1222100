package org.example.repository;

import org.example.domain.Physician;

import java.util.List;
import java.util.UUID;

public interface PhysicianRepository {
    void save(Physician physician);
    Physician findById(UUID id);
    List<Physician> findAll();
}
