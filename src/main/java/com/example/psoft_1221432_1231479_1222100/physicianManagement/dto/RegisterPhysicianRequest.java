package com.example.psoft_1221432_1231479_1222100.physicianManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPhysicianRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String specialtyId;

    private String contactInfo;
    private String workingHours;
    private String workingDays;
}
