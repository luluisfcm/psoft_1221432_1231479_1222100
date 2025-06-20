package com.example.psoft_1221432_1231479_1222100.setup;

import com.example.psoft_1221432_1231479_1222100.userManagement.model.*;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;


@Component
@RequiredArgsConstructor
public class DataBootstrap implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final SpecialtyRepository specialtyRepository;
    private final PhysicianRepository physicianRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public void run(String... args) {
        preloadDepartments();
        preloadSpecialties();
        preloadPhysicians();
        preloadPatients();
        preloadAppointments();
    }

    private void preloadDepartments() {
        createDepartment("DEPAR1", "C", "Cardiology", "Diagnosis and treatment of heart conditions");
        createDepartment("DEPAR2", "P", "Pediatrics", "Medical care for children and adolescents");
    }

    private void preloadSpecialties() {
        createSpecialty("SPCIAL1", "Cardiology");
        createSpecialty("SPCIAL2", "Pediatrics");
    }

    private void createDepartment(String id, String code, String name, String description) {
        if (!departmentRepository.existsByCode(code)) {
            departmentRepository.save(new Department(id, code, name, description));
        }
    }

    private void createSpecialty(String id, String name) {
        if (!specialtyRepository.existsByName(name)) {
            specialtyRepository.save(new Specialty(id, name));
        }
    }

    private void preloadPhysicians() {
        Specialty cardiology = specialtyRepository.findByName("Cardiology").orElseThrow();
        Specialty pediatrics = specialtyRepository.findByName("Pediatrics").orElseThrow();

        createPhysician("DOC1", "Dr. João Carvalho", cardiology, "joao@hospital.com", "MON-FRI", "08:00-16:00");
        createPhysician("DOC2", "Dr. Pedro Pereira", pediatrics, "pedro@hospital.com", "SAT-SUN", "10:00-18:00");
    }

    private void preloadPatients() {
        createPatient("PAT1", "Charlie Brown", "charlie@domain.com", "Rua A", LocalDate.of(1990, 5, 20), "912345678", "ABC123", "Diabetes", null, true);
        createPatient("PAT2", "Diana Smith", "diana@domain.com", "Rua B", LocalDate.of(1985, 8, 15), "923456789", "DEF456", null, null, true);
    }

    private void preloadAppointments() {
        Physician physician1 = physicianRepository.findById("DOC1").orElseThrow();
        Physician physician2 = physicianRepository.findById("DOC2").orElseThrow();

        Patient patient1 = patientRepository.findById("PAT1").orElseThrow();
        Patient patient2 = patientRepository.findById("PAT2").orElseThrow();

        createAppointment("APT1", physician1, patient1, LocalDate.now().minusDays(3), "10:00", "Check-up", "COMPLETED", "10:19");
        createAppointment("APT2", physician1, patient1, LocalDate.now().minusDays(3), "10:20", "Check-up", "COMPLETED", "10:29");
        createAppointment("APT3", physician2, patient2, LocalDate.now().plusDays(2), "14:00", "Follow-up", "SCHEDULED", "14:17");
    }

    private void createPhysician(String id, String name, Specialty specialty, String contactInfo, String workingDays, String workingHours) {
        if (!physicianRepository.existsById(id)) {
            Physician physician = new Physician();
            physician.setId(id);
            physician.setName(name);
            physician.setSpecialty(specialty);
            physician.setContactInfo(contactInfo);
            physician.setWorkingDays(workingDays);
            physician.setWorkingHours(workingHours);
            physicianRepository.save(physician);
        }
    }

    private void createPatient(String id, String name, String email, String morada, LocalDate dob, String phone, String insuranceInfo, String healthConcerns, String photo, boolean dataConsent) {
        if (!patientRepository.existsById(id)) {
            Patient patient = new Patient();
            patient.setId(id);
            patient.setName(name);
            patient.setEmail(email);
            patient.setMorada(morada);
            patient.setDob(dob);
            patient.setPhone(phone);
            patient.setInsuranceInfo(insuranceInfo);
            patient.setHealthConcerns(healthConcerns);
            patient.setPhoto(photo);
            patient.setDataConsent(dataConsent);
            patientRepository.save(patient);
        }
    }

    private void createAppointment(String id, Physician physician, Patient patient, LocalDate date, String time, String consultationType, String status, String endTime) {
        if (!appointmentRepository.existsById(id)) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(id);
            appointment.setPhysician(physician);
            appointment.setPatient(patient);
            appointment.setDate(date);
            appointment.setTime(time);
            appointment.setConsultationType(consultationType);
            appointment.setStatus(status);
            appointment.setEndTime(endTime);
            appointmentRepository.save(appointment);
        }
    }
}
