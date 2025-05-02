package org.example;

import java.util.UUID;

public class Department {
    private final UUID id;
    private final String name;

    public Department(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
