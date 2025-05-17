package com.example.psoft_1221432_1231479_1222100.patientmanagement.api;

import com.example.psoft_1221432_1231479_1222100.patientmanagement.dto.PatientDetailsResponse;
import com.example.psoft_1221432_1231479_1222100.patientmanagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/patients")
@RequiredArgsConstructor
public class PatientQueryApi {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDetailsResponse>> searchPatients(
            @RequestParam(required = false) String name) {
        List<PatientDetailsResponse> results = patientService.searchByName(name);
        return ResponseEntity.ok(results);
    }
}
