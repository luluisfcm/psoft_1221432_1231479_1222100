package org.example.domain;

import java.util.UUID;

public class Specialization {
    private final UUID id;
    private final String name;

    public Specialization(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}
