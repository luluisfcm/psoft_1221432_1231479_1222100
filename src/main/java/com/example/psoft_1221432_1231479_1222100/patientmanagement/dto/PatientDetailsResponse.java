package com.example.psoft_1221432_1231479_1222100.patientmanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PatientDetailsResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String insuranceInfo;
    private LocalDate dob;
    private boolean dataConsent;
}
