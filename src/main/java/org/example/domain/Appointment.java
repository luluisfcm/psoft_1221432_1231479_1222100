package org.example.domain;

import java.util.UUID;

public class Appointment {
    private UUID id;
    private UUID patientId;
    private UUID physicianId;
    private String date;
    private String time;
    private String consultationType;

    public Appointment(UUID patientId, UUID physicianId, String date, String time, String consultationType) {
        this.id = UUID.randomUUID();
        this.patientId = patientId;
        this.physicianId = physicianId;
        this.date = date;
        this.time = time;
        this.consultationType = consultationType;
    }

    // GETTERS
    public UUID getId() {
        return id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public UUID getPhysicianId() {
        return physicianId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getConsultationType() {
        return consultationType;
    }

    // SETTERS
    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setConsultationType(String consultationType) {
        this.consultationType = consultationType;
    }
}
