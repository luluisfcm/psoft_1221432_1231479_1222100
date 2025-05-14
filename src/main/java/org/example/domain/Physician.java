package org.example.domain;

import jakarta.persistence.*;

@Entity
public class Physician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialty;
    private String email;
    private String phone;
    private String workingHours;

    public Physician() {}

    public Physician(String name, String specialty, String email, String phone, String workingHours) {
        this.name = name;
        this.specialty = specialty;
        this.email = email;
        this.phone = phone;
        this.workingHours = workingHours;
    }

    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getWorkingHours() { return workingHours; }
    public void setWorkingHours(String workingHours) { this.workingHours = workingHours; }
}
