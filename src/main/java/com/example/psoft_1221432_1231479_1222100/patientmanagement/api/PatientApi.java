package com.example.psoft_1221432_1231479_1222100.patientmanagement.api;


import com.example.psoft_1221432_1231479_1222100.patientmanagement.dto.RegisterPatientRequest;
import com.example.psoft_1221432_1231479_1222100.patientmanagement.dto.PatientIdResponse;
import com.example.psoft_1221432_1231479_1222100.patientmanagement.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/patients")
@RequiredArgsConstructor
public class PatientApi {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientIdResponse> register(@RequestBody @Valid RegisterPatientRequest request) {
        PatientIdResponse response = patientService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
