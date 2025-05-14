package org.example.repository;

import org.example.domain.Physician;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryPhysicianRepository implements PhysicianRepository {
    private final List<Physician> physicians = new ArrayList<>();

    @Override
    public void save(Physician physician) {
        physicians.add(physician);
    }

    @Override
    public Physician findById(UUID id) {
        return physicians.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Physician> findAll() {
        return new ArrayList<>(physicians);
    }
}
