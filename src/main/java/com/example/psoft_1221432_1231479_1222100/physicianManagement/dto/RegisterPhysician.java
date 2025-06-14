package com.example.psoft_1221432_1231479_1222100.physicianManagement.dto;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Specialty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterPhysician {
    private String name;
    private String email;
    private Specialty specialtyId;
    private String contactInfo;
    private String workingHours;
    private String workingDays;
    // getters & setters (ou usa @Data do Lombok)
}
