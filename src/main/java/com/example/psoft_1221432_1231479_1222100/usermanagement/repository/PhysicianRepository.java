package com.example.psoft_1221432_1231479_1222100.usermanagement.repository;


import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Physician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicianRepository extends JpaRepository<Physician, String> {
    // métodos adicionais, se necessário, podem ser definidos aqui
}
