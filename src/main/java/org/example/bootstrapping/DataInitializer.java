package org.example.bootstrapping;

import jakarta.annotation.PostConstruct;
import org.example.domain.*;
import org.example.repository.*;
import org.springframework.stereotype.Component;

import org.example.domain.Physician;
import org.example.repository.PhysicianRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Component
public class DataInitializer {

    private final DepartmentRepository departmentRepo;
    private final SpecializationRepository specializationRepo;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public DataInitializer() {
        this.departmentRepo = new InMemoryDepartmentRepository();
        this.specializationRepo = new InMemorySpecializationRepository();
        this.userRepository = org.example.ApplicationData.userRepository; // instância global
        this.appointmentRepository = new InMemoryAppointmentRepository(); // ou usar ApplicationData se quiser partilhar
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

        // Criar paciente
        User maria = new User();
        maria.setId(UUID.randomUUID());
        maria.setUsername("maria");
        maria.setPassword("maria123");
        maria.setRole(Role.PATIENT);
        userRepository.save(maria);
        System.out.println("✔ Paciente 'maria' criado.");

        // Criar médico
        User joana = new User();
        joana.setId(UUID.randomUUID());
        joana.setUsername("dr.joana");
        joana.setPassword("joana123");
        joana.setRole(Role.PHYSICIAN);
        userRepository.save(joana);
        System.out.println("✔ Médico 'dr.joana' criado.");

        // Criar marcação entre maria e dr.joana
        Appointment appt = new Appointment(
                maria.getId(),
                joana.getId(),
                "2025-05-30",
                "10:00",
                "cardiology"
        );
        appointmentRepository.save(appt);
        System.out.println("✔ Marcação default criada entre maria e dr.joana.");
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
