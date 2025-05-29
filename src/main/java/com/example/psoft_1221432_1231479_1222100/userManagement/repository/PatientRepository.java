package com.example.psoft_1221432_1231479_1222100.userManagement.repository;

import com.example.psoft_1221432_1231479_1222100.userManagement.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    boolean existsByEmail(String email);
    List<Patient> findByNameContainingIgnoreCase(String name);
}
