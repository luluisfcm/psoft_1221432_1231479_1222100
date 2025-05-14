package org.example.repository;

import org.example.domain.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface DepartmentRepository {
    void save(Department department);
    boolean existsAny();
    Department findById(UUID id);
    List<Department> findAll();
}

