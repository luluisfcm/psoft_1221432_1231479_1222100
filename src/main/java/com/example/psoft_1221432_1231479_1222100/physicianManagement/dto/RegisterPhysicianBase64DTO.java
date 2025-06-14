package com.example.psoft_1221432_1231479_1222100.physicianManagement.dto;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Specialty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterPhysicianBase64DTO {
    private String name;
    private Specialty specialtyId;
    private String contactInfo;
    private String workingHours;
    private String workingDays;
    private String photoBase64;
}
