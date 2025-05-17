package com.example.psoft_1221432_1231479_1222100.userManagement.repository;

import com.example.psoft_1221432_1231479_1222100.userManagement.model.Physician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicianRepository extends JpaRepository<Physician, String> {

    List<Physician> findByNameContainingIgnoreCase(String name);

    List<Physician> findBySpecialty_NameContainingIgnoreCase(String specialty);

    List<Physician> findByNameContainingIgnoreCaseAndSpecialty_NameContainingIgnoreCase(String name, String specialty);
}
