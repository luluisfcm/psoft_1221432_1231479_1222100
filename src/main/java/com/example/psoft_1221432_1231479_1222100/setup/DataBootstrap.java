package com.example.psoft_1221432_1231479_1222100.setup;

import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Department;
import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Specialty;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.DepartmentRepository;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataBootstrap implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final SpecialtyRepository specialtyRepository;

    @Override
    public void run(String... args) {
        preloadDepartments();
        preloadSpecialties();
    }

    private void preloadDepartments() {
        createDepartment("DEPAR1", "C", "Cardiology", "Diagnosis and treatment of heart conditions");
        createDepartment("DEPAR2", "P", "Pediatrics", "Medical care for children and adolescents");
        createDepartment("DEPAR3", "O", "Orthopedics", "Treatment of bones, joints, and muscles");
        createDepartment("DEPAR4", "D", "Dermatology", "Treatment of skin, hair, and nail disorders");
    }


    private void preloadSpecialties() {
        createSpecialty("SPCIAL1", "Cardiology");
        createSpecialty("SPCIAL2", "Neurology");
        createSpecialty("SPCIAL3", "Orthopedics");
        createSpecialty("SPCIAL4", "Pediatrics");
    }


    private void createDepartment(String id, String code, String name, String description) {
        if (!departmentRepository.existsByCode(code)) {
            Department department = new Department(id, code, name, description);
            departmentRepository.save(department);
        }
    }

    private void createSpecialty(String id, String name) {
        if (!specialtyRepository.existsByName(name)) {
            Specialty specialty = new Specialty(id, name);
            specialtyRepository.save(specialty);
        }
    }
}
