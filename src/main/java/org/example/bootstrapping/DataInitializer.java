package org.example.bootstrapping;

import jakarta.annotation.PostConstruct;
import org.example.domain.Department;
import org.example.domain.Specialization;
import org.example.repository.DepartmentRepository;
import org.example.repository.SpecializationRepository;
import org.example.repository.InMemoryDepartmentRepository;
import org.example.repository.InMemorySpecializationRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataInitializer {

    private final DepartmentRepository departmentRepo = new InMemoryDepartmentRepository();
    private final SpecializationRepository specializationRepo = new InMemorySpecializationRepository();

    @PostConstruct
    public void init() {
        if (!departmentRepo.existsAny()) {
            departmentRepo.save(new Department("Cardiology"));
            departmentRepo.save(new Department("Neurology"));
            departmentRepo.save(new Department("Pediatrics"));
            System.out.println("✔ Departamentos carregados.");
        }

        if (!specializationRepo.existsAny()) {
            specializationRepo.save(new Specialization("Pediatric Cardiology", UUID.randomUUID()));
            specializationRepo.save(new Specialization("Neurosurgery", UUID.randomUUID()));
            specializationRepo.save(new Specialization("Endocrinology", UUID.randomUUID()));
            System.out.println("✔ Especializações carregadas.");
        }
    }
}
