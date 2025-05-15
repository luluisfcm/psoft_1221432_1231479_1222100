package org.example.domain;
import java.time.LocalDate;
import java.util.UUID;

public class Patient {

    private UUID id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String phone;
    private String insurance;
    private boolean consent;

    public Patient(String name, String email, LocalDate dateOfBirth, String phone, String insurance, boolean consent) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.insurance = insurance;
        this.consent = consent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public boolean isConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
