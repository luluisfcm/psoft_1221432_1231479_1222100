package com.example.psoft_1221432_1231479_1222100.userManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "PHYSICIAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Physician {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "specialty_id", referencedColumnName = "specialtyId")
    private Specialty specialty;

    @Column
    private String contactInfo;

    @Column
    private String workingDays;

    @Column
    private String workingHours;

    @Column
    private String photoUrl;

    public Physician(String id, String firstName, String contactInfo, String workingDays, String workingHours) {
        this.id = id;
        this.name = firstName;
        this.contactInfo = contactInfo;
        this.workingDays = workingDays;
        this.workingHours = workingHours;
    }
}

