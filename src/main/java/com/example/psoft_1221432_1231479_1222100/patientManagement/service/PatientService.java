package com.example.psoft_1221432_1231479_1222100.patientManagement.service;

import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.AppointmentHistoryResponse;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.ScheduleAppointmentRequest;
import com.example.psoft_1221432_1231479_1222100.patientManagement.dto.*;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Appointment;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Patient;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Physician;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.AppointmentRepository;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.PatientRepository;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.PhysicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
    @Autowired
    private final PatientRepository patientRepository;
    @Autowired
    private PhysicianRepository physicianRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;


    public PatientIdResponse register(RegisterPatientRequest request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Patient with this email already exists.");
        }

        Patient patient = new Patient(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getEmail(),
                request.getMorada(),
                request.getDob(),
                request.getPhone(),
                request.getInsuranceInfo(),
                request.getHealthConcerns(),
                request.getPhoto(),
                request.isDataConsent()
        );

        Patient saved = patientRepository.save(patient);

        return PatientIdResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .build();
    }

    public PatientDetailsResponse getById(String id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        return PatientDetailsResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .insuranceInfo(patient.getInsuranceInfo())
                .dob(patient.getDob())
                .dataConsent(patient.isDataConsent())
                .build();
    }

    public List<PatientDetailsResponse> searchByName(String name) {
        List<Patient> patients;

        if (name == null || name.isBlank()) {
            patients = patientRepository.findAll();
        } else {
            patients = patientRepository.findByNameContainingIgnoreCase(name);
        }

        return patients.stream().map(p ->
                PatientDetailsResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .email(p.getEmail())
                        .phone(p.getPhone())
                        .insuranceInfo(p.getInsuranceInfo())
                        .dob(p.getDob())
                        .dataConsent(p.isDataConsent())
                        .build()
        ).toList();
    }

    public PatientDetailsResponse update(String patientId, UpdatePatientRequest request) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        // Atualiza os campos permitidos
        patient.setName(request.name());
        patient.setEmail(request.email());
        patient.setPhone(request.phone());
        patient.setMorada(request.morada());
        patient.setInsuranceInfo(request.insuranceInfo());
        patient.setHealthConcerns(request.healthConcerns());

        Patient updated = patientRepository.save(patient);

        return PatientDetailsResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .email(updated.getEmail())
                .phone(updated.getPhone())
                .insuranceInfo(updated.getInsuranceInfo())
                .dob(updated.getDob())
                .dataConsent(updated.isDataConsent())
                .build();
    }
    public void scheduleAppointment(ScheduleAppointmentRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Physician physician = physicianRepository.findById(request.getPhysicianId())
                .orElseThrow(() -> new IllegalArgumentException("Physician not found"));

        // Validar dia da semana
        Set<DayOfWeek> workingDays = parseWorkingDays(physician.getWorkingDays());
        if (!workingDays.contains(request.getDate().getDayOfWeek())) {
            throw new IllegalArgumentException("Physician does not work on this day.");
        }

        // Validar hora
        String[] hours = physician.getWorkingHours().split("-");
        LocalTime start = LocalTime.parse(hours[0]);
        LocalTime end = LocalTime.parse(hours[1]);
        LocalTime requestedTime = LocalTime.parse(request.getTime());

        if (requestedTime.isBefore(start) || requestedTime.plusMinutes(20).isAfter(end)) {
            throw new IllegalArgumentException("Requested time is outside working hours.");
        }

        // Verificar se já existe marcação
        boolean exists = appointmentRepository.existsByPhysicianIdAndDateAndTime(
                physician.getId(),
                request.getDate(),
                request.getTime()
        );

        if (exists) {
            throw new IllegalArgumentException("Slot already taken.");
        }

        // Criar marcação
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(UUID.randomUUID().toString());
        appointment.setPhysician(physician);
        appointment.setPatient(patient);
        appointment.setDate(request.getDate());
        appointment.setTime(request.getTime());
        appointment.setConsultationType(request.getConsultationType());
        appointment.setStatus("SCHEDULED");

        appointmentRepository.save(appointment);
    }
    private Set<DayOfWeek> parseWorkingDays(String workingDaysRaw) {
        Set<DayOfWeek> days = new HashSet<>();

        if (workingDaysRaw == null || workingDaysRaw.isBlank()) return days;

        for (String part : workingDaysRaw.toUpperCase().split(",")) {
            part = part.trim();
            switch (part) {
                case "MON-FRI" -> days.addAll(List.of(
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY,
                        DayOfWeek.FRIDAY
                ));
                case "SAT-SUN" -> days.addAll(List.of(
                        DayOfWeek.SATURDAY,
                        DayOfWeek.SUNDAY
                ));
                default -> days.add(DayOfWeek.valueOf(part));
            }
        }

        return days;
    }
    public List<AppointmentHistoryResponse> getAppointmentHistory(String patientId) {
        return appointmentRepository.findByPatientIdOrderByDateDesc(patientId)
                .stream()
                .map(a -> new AppointmentHistoryResponse(
                        a.getAppointmentId(),
                        a.getPhysician().getName(),
                        a.getConsultationType(),
                        a.getDate(),
                        a.getTime(),
                        a.getStatus()
                ))
                .toList();
    }

}
