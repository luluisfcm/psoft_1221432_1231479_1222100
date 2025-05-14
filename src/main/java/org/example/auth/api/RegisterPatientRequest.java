package org.example.auth.api;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class RegisterPatientRequest {

    @NotBlank
    private String name;

    @Email @NotBlank
    private String email;

    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    private String phone;

    private String insurance;

    @AssertTrue(message = "Consent is required")
    private boolean consent;

    public @NotBlank String getName() {
        return name;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }
// Getters e setters
}