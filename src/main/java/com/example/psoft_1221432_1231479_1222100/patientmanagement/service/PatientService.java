package com.example.psoft_1221432_1231479_1222100.patientmanagement.service;

import com.example.psoft_1221432_1231479_1222100.patientmanagement.dto.RegisterPatientRequest;
import com.example.psoft_1221432_1231479_1222100.patientmanagement.dto.PatientIdResponse;
import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Patient;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
