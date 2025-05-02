package org.example;

import java.util.UUID;

public class Specialization {
    private UUID id;
    private String name;
    private UUID departmentId;

    public Specialization(String name, UUID departmentId) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.departmentId = departmentId;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public UUID getDepartmentId() { return departmentId; }
}
