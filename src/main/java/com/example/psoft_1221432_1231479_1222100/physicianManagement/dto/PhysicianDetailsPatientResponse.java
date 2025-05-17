package com.example.psoft_1221432_1231479_1222100.physicianManagement.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhysicianDetailsPatientResponse {
    private String id;
    private String name;
    private String specialty;
}