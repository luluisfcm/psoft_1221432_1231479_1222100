package com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentUpdateRequest {
    private LocalDate date;
    private String time; // formato "HH:mm"
    private String consultationType;
}