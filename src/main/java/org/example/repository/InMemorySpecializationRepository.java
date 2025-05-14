package org.example.repository;

import org.example.domain.Specialization;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class InMemorySpecializationRepository implements SpecializationRepository {
    private final List<Specialization> specializations = new ArrayList<>();

    @Override
    public void save(Specialization specialization) {
        specializations.add(specialization);
        System.out.println("Specialization saved: " + specialization.getName());
    }

    @Override
    public boolean existsAny() {
        return !specializations.isEmpty();
    }

    @Override
    public List<Specialization> findAll() {
        return new ArrayList<>(specializations);
    }
}
