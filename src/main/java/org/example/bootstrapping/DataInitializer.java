package org.example.bootstrapping;

import jakarta.annotation.PostConstruct;
import org.example.domain.Department;
import org.example.domain.Specialization;
import org.example.repository.DepartmentRepository;
import org.example.repository.SpecializationRepository;
import org.example.repository.InMemoryDepartmentRepository;
import org.example.repository.InMemorySpecializationRepository;
import org.springframework.stereotype.Component;

import org.example.domain.Physician;
import org.example.repository.PhysicianRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Component
public class DataInitializer {

    private DepartmentRepository departmentRepo = new InMemoryDepartmentRepository();
    private SpecializationRepository specializationRepo = new InMemorySpecializationRepository();

    public DataInitializer(DepartmentRepository departmentRepo,
                           SpecializationRepository specializationRepo) {
        this.departmentRepo = departmentRepo;
        this.specializationRepo = specializationRepo;
    }

    @PostConstruct
    public void init() {
        if (!departmentRepo.existsAny()) {
            departmentRepo.save(new Department("Cardiology"));
            departmentRepo.save(new Department("Neurology"));
            departmentRepo.save(new Department("Pediatrics"));
            System.out.println("Departamentos carregados.");
        }

        if (!specializationRepo.existsAny()) {
            specializationRepo.save(new Specialization("Pediatric Cardiology", UUID.randomUUID()));
            specializationRepo.save(new Specialization("Neurosurgery", UUID.randomUUID()));
            specializationRepo.save(new Specialization("Endocrinology", UUID.randomUUID()));
            System.out.println("Especializações carregadas.");
        }
    }

    @Bean
    CommandLineRunner initDatabase(PhysicianRepository physicianRepository) {
        return args -> {
            if (physicianRepository.count() == 0) {
                Physician defaultPhysician = new Physician();
                defaultPhysician.setName("Dr. João Silva");
                defaultPhysician.setSpecialty("Cardiologia");
                defaultPhysician.setEmail("joao.silva@clinic.pt");
                defaultPhysician.setPhone("912345678");
                defaultPhysician.setWorkingHours("Mon-Fri 08:00-16:00");

                physicianRepository.save(defaultPhysician);

                System.out.println("Physician por defeito criado: " + defaultPhysician.getName());
            }
        };
    }
}
