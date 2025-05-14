package org.example.controller;

import org.example.domain.Department;
import org.example.domain.Specialization;
import org.example.repository.DepartmentRepository;
import org.example.repository.SpecializationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/setup")
public class SetupController {

    private final DepartmentRepository departmentRepo;
    private final SpecializationRepository specializationRepo;

    public SetupController(DepartmentRepository departmentRepo,
                           SpecializationRepository specializationRepo) {
        this.departmentRepo = departmentRepo;
        this.specializationRepo = specializationRepo;
    }

    @GetMapping("/preloaded")
    public ResponseEntity<Map<String, Object>> getPreloadedData() {
        List<Department> departments = departmentRepo.findAll();
        List<Specialization> specializations = specializationRepo.findAll();

        Map<String, Object> result = new HashMap<>();
        result.put("departments", departments);
        result.put("specializations", specializations);

        return ResponseEntity.ok(result);
    }
}
