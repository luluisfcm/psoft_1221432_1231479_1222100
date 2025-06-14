package com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingAppointment {
    private String appointmentId;
    private String patientName;
    private String physicianName;
    private LocalDate date;
    private String time;
    private String status;
}