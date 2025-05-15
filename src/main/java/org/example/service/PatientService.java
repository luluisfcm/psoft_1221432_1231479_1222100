package org.example.service;

import org.example.domain.Patient;
import org.example.auth.api.PatientRegistrationRequest;
import org.example.auth.api.PatientRegistrationResponse;
import org.example.repository.PatientRepository;

import java.util.UUID;

public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientRegistrationResponse register(PatientRegistrationRequest request) {
        if (!request.isDataConsent()) {
            throw new IllegalArgumentException("Consentimento de dados é obrigatório.");
        }

        Patient patient = new Patient();
        patient.setId(UUID.randomUUID());
        patient.setName(request.getName());
        patient.setEmail(request.getEmail());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setInsuranceInfo(request.getInsuranceInfo());
        patient.setDataConsent(true);

        patientRepository.save(patient);

        return new PatientRegistrationResponse(patient.getId(), patient.getName());
    }
}
