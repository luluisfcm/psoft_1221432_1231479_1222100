package com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto;

import java.time.LocalDate;

public record AppointmentHistoryResponse(
        String appointmentId,
        String physicianName,
        String consultationType,
        LocalDate date,
        String time,
        String status
) {}
