package com.example.psoft_1221432_1231479_1222100.userManagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "APPOINTMENT")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {

    @Id
    private String appointmentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "physician_id")
    private Physician physician;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String time; // formato ex: "14:30"

    @Column(nullable = false)
    private String consultationType;

    @Column(nullable = false)
    private String status = "SCHEDULED"; // default

    public void cancel() {
        this.status = "CANCELLED";
    }

    public void update(String newTime, LocalDate newDate, String newType) {
        this.time = newTime;
        this.date = newDate;
        this.consultationType = newType;
    }
}
