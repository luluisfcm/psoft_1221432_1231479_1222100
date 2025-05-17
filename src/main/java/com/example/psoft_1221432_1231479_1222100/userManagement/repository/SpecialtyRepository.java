package com.example.psoft_1221432_1231479_1222100.userManagement.repository;

import com.example.psoft_1221432_1231479_1222100.userManagement.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, String> {
    boolean existsByName(String name);

    Optional<Specialty> findByName(String name);
}
