package com.example.psoft_1221432_1231479_1222100.patientmanagement.service;

import com.example.psoft_1221432_1231479_1222100.patientmanagement.dto.PatientDetailsResponse;
import com.example.psoft_1221432_1231479_1222100.patientmanagement.dto.RegisterPatientRequest;
import com.example.psoft_1221432_1231479_1222100.patientmanagement.dto.PatientIdResponse;
import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Patient;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

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
}
