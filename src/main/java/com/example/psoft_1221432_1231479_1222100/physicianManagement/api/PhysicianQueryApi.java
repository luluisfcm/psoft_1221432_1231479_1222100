package com.example.psoft_1221432_1231479_1222100.physicianManagement.api;

import com.example.psoft_1221432_1231479_1222100.auth.api.AuthHelper;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.PhysicianDetailsPatientResponse;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.service.PhysicianService;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/physicians")
@RequiredArgsConstructor
public class PhysicianQueryApi {

    private final PhysicianService physicianService;
    private final AuthHelper authHelper;

    @GetMapping("/{physicianId}")
    public ResponseEntity<?> getPhysician(@PathVariable String physicianId) {
        Role role = authHelper.getCurrentUser().getRole();
        Object response = physicianService.getByIdForRole(physicianId, role);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<PhysicianDetailsPatientResponse>> searchPhysicians(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialty) {

        if (!authHelper.isPatient()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<PhysicianDetailsPatientResponse> results = physicianService.search(name, specialty);
        return ResponseEntity.ok(results);
    }

}

