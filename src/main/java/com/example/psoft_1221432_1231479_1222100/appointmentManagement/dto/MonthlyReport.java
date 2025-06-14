package com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyReport {
    private String month;           // "2025-06" ou "June 2025"
    private long totalAppointments;
    private long cancellations;
    private long reschedules;
}