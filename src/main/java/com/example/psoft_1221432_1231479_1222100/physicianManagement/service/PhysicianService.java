package com.example.psoft_1221432_1231479_1222100.physicianManagement.service;

import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.PhysicianDetailsAdminResponse;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.PhysicianDetailsPatientResponse;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.PhysicianIdResponse;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.RegisterPhysicianRequest;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Physician;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Role;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Specialty;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.PhysicianRepository;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhysicianService {

    private final PhysicianRepository physicianRepository;
    private final SpecialtyRepository specialtyRepository;

    public PhysicianIdResponse register(RegisterPhysicianRequest request) {
        Specialty specialty = specialtyRepository.findById(request.getSpecialtyId())
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found"));

        Physician physician = new Physician();
        physician.setId(UUID.randomUUID().toString());
        physician.setName(request.getName());
        physician.setSpecialty(specialty);
        physician.setContactInfo(request.getContactInfo());
        physician.setWorkingHours(request.getWorkingHours());
        physician.setWorkingDays(request.getWorkingDays());

        Physician saved = physicianRepository.save(physician);

        return PhysicianIdResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .build();
    }

    public Object getByIdForRole(String id, Role role) {
        Physician physician = physicianRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Physician not found"));

        if (role == Role.ADMIN) {
            return PhysicianDetailsAdminResponse.builder()
                    .id(physician.getId())
                    .name(physician.getName())
                    .specialty(physician.getSpecialty().getName())
                    .contactInfo(physician.getContactInfo())
                    .workingHours(physician.getWorkingHours())
                    .workingDays(physician.getWorkingDays())
                    .build();
        } else {
            return PhysicianDetailsPatientResponse.builder()
                    .id(physician.getId())
                    .name(physician.getName())
                    .specialty(physician.getSpecialty().getName())
                    .build();
        }
    }

    public List<PhysicianDetailsPatientResponse> search(String name, String specialty) {
        List<Physician> results;

        if (name != null && specialty != null) {
            results = physicianRepository.findByNameContainingIgnoreCaseAndSpecialty_NameContainingIgnoreCase(name, specialty);
        } else if (name != null) {
            results = physicianRepository.findByNameContainingIgnoreCase(name);
        } else if (specialty != null) {
            results = physicianRepository.findBySpecialty_NameContainingIgnoreCase(specialty);
        } else {
            results = physicianRepository.findAll();
        }

        return results.stream().map(p ->
                PhysicianDetailsPatientResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .specialty(p.getSpecialty().getName())
                        .build()
        ).toList();
    }


}
