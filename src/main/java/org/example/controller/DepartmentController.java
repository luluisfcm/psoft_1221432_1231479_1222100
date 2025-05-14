package org.example.controller;

import org.example.domain.Department;
import org.example.repository.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentRepository repository;

    public DepartmentController() {
        this.repository = new org.example.repository.InMemoryDepartmentRepository(); // Usa a versão in-memory
    }

    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) {
        Department newDept = new Department(department.getName());
        repository.save(newDept);
        return ResponseEntity.ok(newDept);
    }
}
