package com.example.psoft_1221432_1231479_1222100.appointmentManagement.api;

import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.*;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.services.AppointmentService;
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
@RequestMapping("/api/admin/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Endpoints for scheduling appointments by administrators")
public class AppointmentApi {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(
            summary = "Schedule an appointment",
            description = "Allows an administrator to schedule an appointment for a patient with a physician. Assumes physician availability.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Appointment scheduled successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Patient or Physician not found"),
                    @ApiResponse(responseCode = "409", description = "Slot already booked"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    public ResponseEntity<AppointmentIdResponse> schedule(
            @RequestBody @Valid ScheduleAppointmentRequest request) {
        AppointmentIdResponse response = appointmentService.schedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/top-physicians")
    public ResponseEntity<List<TopPhysicianResponse>> getTopPhysicians() {
        return ResponseEntity.ok(appointmentService.getTop5Physicians());
    }

    @GetMapping("/admin/stats/appointments-by-age-group")
    public ResponseEntity<List<AppointmentAgeGroupStats>> getStatsByAgeGroup() {
        List<AppointmentAgeGroupStats> stats = appointmentService.getAppointmentStatsByAgeGroup();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/upcoming-appointments")
    public ResponseEntity<List<UpcomingAppointment>> listUpcomingAppointments() {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointments());
    }

    @GetMapping("/avg-appointment-duration-per-physician")
    public ResponseEntity<List<AppointmentAvgDuration>> getAvgAppointmentDurationPerPhysician() {
        return ResponseEntity.ok(appointmentService.getAverageDurationPerPhysician());
    }
}
