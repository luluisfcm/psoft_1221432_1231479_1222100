package com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleAppointmentRequest {

    @NotBlank
    private String physicianId;

    @NotBlank
    private String patientId;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String time;

    @NotBlank
    private String consultationType;
}
