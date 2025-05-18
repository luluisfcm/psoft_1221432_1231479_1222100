package com.example.psoft_1221432_1231479_1222100.appointmentManagement.api;

import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.AppointmentUpdateRequest;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.AppointmentViewResponse;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.services.AppointmentService;
import com.example.psoft_1221432_1231479_1222100.auth.api.AuthHelper;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/appointments")
@RequiredArgsConstructor
@Tag(name = "Patient Appointments", description = "Endpoints for patients to view, update and cancel their appointments")
public class AppointmentPatientApi {

    private final AppointmentService appointmentService;
    private final AuthHelper authHelper;

    @GetMapping("/by-patient/{patientId}")
    @Operation(
            summary = "Get appointments by patient ID",
            description = "Allows admins to view all appointments for a specific patient",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of appointments returned"),
                    @ApiResponse(responseCode = "403", description = "Access forbidden")
            }
    )
    public ResponseEntity<List<AppointmentViewResponse>> getAppointmentsByPatientId(@PathVariable String patientId) {
        List<AppointmentViewResponse> appointments = appointmentService.getAppointmentsForPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{appointmentId}")
    @Operation(
            summary = "Update an appointment",
            description = "Allows patients or admins to update an appointment’s date, time or type",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
                    @ApiResponse(responseCode = "403", description = "Access denied"),
                    @ApiResponse(responseCode = "404", description = "Appointment not found")
            }
    )
    public ResponseEntity<AppointmentViewResponse> update(
            @PathVariable String appointmentId,
            @RequestBody @Valid AppointmentUpdateRequest request) {
        if (!authHelper.isPatient() && !authHelper.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        AppointmentViewResponse updated = appointmentService.updateAndReturn(appointmentId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{appointmentId}")
    @Operation(
            summary = "Cancel an appointment",
            description = "Allows patients or admins to cancel a scheduled appointment",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointment cancelled successfully"),
                    @ApiResponse(responseCode = "403", description = "Access denied"),
                    @ApiResponse(responseCode = "404", description = "Appointment not found")
            }
    )
    public ResponseEntity<AppointmentViewResponse> cancel(@PathVariable String appointmentId) {
        if (!authHelper.isPatient() && !authHelper.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        AppointmentViewResponse cancelled = appointmentService.cancelAndReturn(appointmentId);
        return ResponseEntity.ok(cancelled);
    }

    @GetMapping("/{appointmentId}")
    @Operation(
            summary = "Get appointment details by ID",
            description = "Allows a patient or admin to retrieve full appointment information",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointment details returned"),
                    @ApiResponse(responseCode = "403", description = "Access denied"),
                    @ApiResponse(responseCode = "404", description = "Appointment not found")
            }
    )
    public ResponseEntity<AppointmentViewResponse> getAppointmentById(@PathVariable String appointmentId) {
        User currentUser = authHelper.getCurrentUser();
        AppointmentViewResponse response = appointmentService.getAppointmentById(appointmentId, currentUser);
        return ResponseEntity.ok(response);
    }
}
