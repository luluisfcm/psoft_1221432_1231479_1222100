package org.example.repository;

import org.example.domain.Physician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Long> {

    List<Physician> findByNameContainingIgnoreCase(String name);

    List<Physician> findBySpecialtyContainingIgnoreCase(String specialty);

    List<Physician> findByNameContainingIgnoreCaseOrSpecialtyContainingIgnoreCase(String name, String specialty);
}
