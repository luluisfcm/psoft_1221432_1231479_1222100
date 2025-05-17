package com.example.psoft_1221432_1231479_1222100.patientManagement.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterPatientRequest {

    @NotBlank
    private String name;

    @NotBlank @Email
    private String email;

    private String morada;

    @NotNull
    private LocalDate dob;

    private String phone;
    private String insuranceInfo;
    private String healthConcerns;
    private String photo;

    @AssertTrue(message = "Data consent is required")
    private boolean dataConsent;
}
