package com.example.psoft_1221432_1231479_1222100.appointmentManagement.api;

import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.AppointmentViewResponse;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.services.AppointmentService;
import com.example.psoft_1221432_1231479_1222100.auth.api.AuthHelper;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/appointments")
@RequiredArgsConstructor
public class AppointmentPatientApi {

    private final AppointmentService appointmentService;
    private final AuthHelper authHelper;

    @GetMapping("/{patientId}")
    public ResponseEntity<List<AppointmentViewResponse>> getAppointmentsByPatientId(@PathVariable String patientId) {
        List<AppointmentViewResponse> appointments = appointmentService.getAppointmentsForPatient(patientId);
        return ResponseEntity.ok(appointments);
    }
}
