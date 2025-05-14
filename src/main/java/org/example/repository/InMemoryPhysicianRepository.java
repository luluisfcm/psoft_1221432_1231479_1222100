package org.example.repository;

import org.example.domain.Physician;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<Physician> findByName(String name) {
        return physicians.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Physician> findBySpecialization(String specializationName) {
        return physicians.stream()
                .filter(p -> p.getSpecialization().getName().toLowerCase().contains(specializationName.toLowerCase()))
                .collect(Collectors.toList());
    }
}
