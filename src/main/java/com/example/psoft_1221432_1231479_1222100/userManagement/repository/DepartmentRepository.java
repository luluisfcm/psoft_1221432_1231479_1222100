package com.example.psoft_1221432_1231479_1222100.userManagement.repository;

import com.example.psoft_1221432_1231479_1222100.userManagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    boolean existsByCode(String code);

    Optional<Department> findByCode(String code);
}