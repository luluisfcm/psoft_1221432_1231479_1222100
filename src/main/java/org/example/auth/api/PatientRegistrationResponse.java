package org.example.auth.api;

import java.util.UUID;

public class PatientRegistrationResponse {
    private UUID id;
    private String name;

    public PatientRegistrationResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
