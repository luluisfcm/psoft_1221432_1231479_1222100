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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
