package org.example.domain;

import java.util.UUID;

public class Physician {
    private final UUID id;
    private final String name;
    private final String contactInfo;
    private final String workingHours;
    private final Specialization specialization;

    public Physician(String name, String contactInfo, String workingHours, Specialization specialization) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.contactInfo = contactInfo;
        this.workingHours = workingHours;
        this.specialization = specialization;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nNome: " + name +
                "\nContato: " + contactInfo +
                "\nHorário de Trabalho: " + workingHours +
                "\nEspecialidade: " + specialization.getName();
    }
}
