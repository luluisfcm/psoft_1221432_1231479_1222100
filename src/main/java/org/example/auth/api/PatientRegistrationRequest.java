package org.example.auth.api;

import java.time.LocalDate;

public class PatientRegistrationRequest {
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String insuranceInfo;
    private boolean dataConsent;

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getInsuranceInfo() {
        return insuranceInfo;
    }

    public boolean isDataConsent() {
        return dataConsent;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setInsuranceInfo(String insuranceInfo) {
        this.insuranceInfo = insuranceInfo;
    }

    public void setDataConsent(boolean dataConsent) {
        this.dataConsent = dataConsent;
    }
}
