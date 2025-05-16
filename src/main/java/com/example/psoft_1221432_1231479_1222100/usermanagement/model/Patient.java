package com.example.psoft_1221432_1231479_1222100.usermanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "PATIENT")
@Getter
@Setter
public class Patient {

    @Id
    private String id; // UUID em formato String (consistência com outras entidades)

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String morada;

    @Column(nullable = false)
    private LocalDate dob;

    @Column
    private String phone;

    @Column
    private String insuranceInfo;

    @Column
    private String healthConcerns;

    @Column
    private String photo;

    @Column(nullable = false)
    private boolean dataConsent;

    public Patient(String id, String name, String email, String morada, LocalDate dob, String phone,
                   String insuranceInfo, String healthConcerns, String photo, boolean dataConsent) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.morada = morada;
        this.dob = dob;
        this.phone = phone;
        this.insuranceInfo = insuranceInfo;
        this.healthConcerns = healthConcerns;
        this.photo = photo;
        this.dataConsent = dataConsent;
    }

    protected Patient() {
    }
}
