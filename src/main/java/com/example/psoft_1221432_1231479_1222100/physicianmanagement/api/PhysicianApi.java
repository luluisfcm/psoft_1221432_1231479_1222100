package com.example.psoft_1221432_1231479_1222100.physicianmanagement.api;

import com.example.psoft_1221432_1231479_1222100.physicianmanagement.dto.PhysicianIdResponse;
import com.example.psoft_1221432_1231479_1222100.physicianmanagement.dto.RegisterPhysicianRequest;
import com.example.psoft_1221432_1231479_1222100.physicianmanagement.service.PhysicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/physicians")
@RequiredArgsConstructor
public class PhysicianApi {

    private final PhysicianService physicianService;

    @PostMapping("/register")
    public ResponseEntity<PhysicianIdResponse> register(
            @RequestBody @Valid RegisterPhysicianRequest request) {
        PhysicianIdResponse response = physicianService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
