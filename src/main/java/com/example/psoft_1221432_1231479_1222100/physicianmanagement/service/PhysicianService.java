package com.example.psoft_1221432_1231479_1222100.physicianmanagement.service;

import com.example.psoft_1221432_1231479_1222100.physicianmanagement.dto.PhysicianIdResponse;
import com.example.psoft_1221432_1231479_1222100.physicianmanagement.dto.RegisterPhysicianRequest;
import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Physician;
import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Specialty;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.PhysicianRepository;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
