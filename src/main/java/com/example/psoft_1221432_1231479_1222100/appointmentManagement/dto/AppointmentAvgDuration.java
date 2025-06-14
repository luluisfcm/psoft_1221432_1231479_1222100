package com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentAvgDuration {
    private String physicianId;
    private String physicianName;
    private double averageDurationMinutes;
}

