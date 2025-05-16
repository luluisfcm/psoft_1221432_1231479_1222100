package com.example.psoft_1221432_1231479_1222100.usermanagement.repository;

import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    boolean existsByEmail(String email);
}
