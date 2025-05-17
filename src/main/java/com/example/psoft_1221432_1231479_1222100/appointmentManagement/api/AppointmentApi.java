package com.example.psoft_1221432_1231479_1222100.appointmentManagement.api;

import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.AppointmentIdResponse;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.ScheduleAppointmentRequest;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/appointments")
@RequiredArgsConstructor
public class AppointmentApi {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentIdResponse> schedule(
            @RequestBody @Valid ScheduleAppointmentRequest request) {
        AppointmentIdResponse response = appointmentService.schedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}