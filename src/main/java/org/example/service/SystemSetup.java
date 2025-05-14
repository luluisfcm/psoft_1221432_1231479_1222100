package org.example.service;

import org.example.domain.Department;
import org.example.domain.Specialization;
import org.example.repository.DepartmentRepository;
import org.example.repository.SpecializationRepository;
import org.example.bootstrapping.BootStrapService;

public class SystemSetup {
    private final BootStrapService bootStrapService;
    private final DepartmentRepository departmentRepository;
    private final SpecializationRepository specializationRepository;

    public SystemSetup(BootStrapService bootStrapService,
                       DepartmentRepository departmentRepository,
                       SpecializationRepository specializationRepository) {
        this.bootStrapService = bootStrapService;
        this.departmentRepository = departmentRepository;
        this.specializationRepository = specializationRepository;
    }

    public void bootstrapAdmins(String username, String password) {
        bootStrapService.initializeAdmin(username, password);
    }

    public void bootstrapDepartments() {
        if (departmentRepository.existsAny()) return;

        Department cardiology = new Department("Cardiology");
        Department neurology = new Department("Neurology");

        departmentRepository.save(cardiology);
        departmentRepository.save(neurology);

        Specialization cardio1 = new Specialization("Interventional Cardiology", cardiology.getId());
        Specialization cardio2 = new Specialization("Non-Invasive Cardiology", cardiology.getId());
        Specialization neuro1 = new Specialization("Pediatric Neurology", neurology.getId());
        Specialization neuro2 = new Specialization("Neurophysiology", neurology.getId());

        specializationRepository.save(cardio1);
        specializationRepository.save(cardio2);
        specializationRepository.save(neuro1);
        specializationRepository.save(neuro2);
    }
}
