package com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentViewResponse {
    private String appointmentId;
    private String physicianName;
    private String date;
    private String time;
    private String consultationType;
    private String status;
}
