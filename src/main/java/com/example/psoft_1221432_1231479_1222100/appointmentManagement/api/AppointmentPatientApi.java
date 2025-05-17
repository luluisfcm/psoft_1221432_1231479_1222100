package com.example.psoft_1221432_1231479_1222100.appointmentManagement.api;

import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.AppointmentUpdateRequest;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.AppointmentViewResponse;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.services.AppointmentService;
import com.example.psoft_1221432_1231479_1222100.auth.api.AuthHelper;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/appointments")
@RequiredArgsConstructor
public class AppointmentPatientApi {

    private final AppointmentService appointmentService;
    private final AuthHelper authHelper;

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<AppointmentViewResponse>> getAppointmentsByPatientId(@PathVariable String patientId) {
        List<AppointmentViewResponse> appointments = appointmentService.getAppointmentsForPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentViewResponse> update(
            @PathVariable String appointmentId,
            @RequestBody AppointmentUpdateRequest request) {
        if (!authHelper.isPatient() && !authHelper.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        AppointmentViewResponse updated = appointmentService.updateAndReturn(appointmentId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<AppointmentViewResponse> cancel(@PathVariable String appointmentId) {
        if (!authHelper.isPatient() && !authHelper.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        AppointmentViewResponse cancelled = appointmentService.cancelAndReturn(appointmentId);
        return ResponseEntity.ok(cancelled);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentViewResponse> getAppointmentById(@PathVariable String appointmentId) {
        User currentUser = authHelper.getCurrentUser();
        AppointmentViewResponse response = appointmentService.getAppointmentById(appointmentId, currentUser);
        return ResponseEntity.ok(response);
    }

}
