package com.example.psoft_1221432_1231479_1222100.patientManagement.dto;

public record UpdatePatientRequest(
        String name,
        String email,
        String phone,
        String morada,
        String insuranceInfo,
        String healthConcerns
) {}