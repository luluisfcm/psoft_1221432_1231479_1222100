package org.example.repository;

import org.example.domain.Specialization;

import java.util.ArrayList;
import java.util.List;

public interface SpecializationRepository {
    void save(Specialization specialization);
    boolean existsAny();
    List<Specialization> findAll();
}

