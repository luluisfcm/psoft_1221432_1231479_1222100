package com.example.psoft_1221432_1231479_1222100.physicianManagement.dto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PhysicianUpdateRequest {
    private String name;
    private String contactInfo;
    private String workingDays;
    private String workingHours;
    private String specialtyId;
}
