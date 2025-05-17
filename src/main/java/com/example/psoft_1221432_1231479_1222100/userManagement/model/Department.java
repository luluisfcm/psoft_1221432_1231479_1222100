package com.example.psoft_1221432_1231479_1222100.userManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DEPARTMENT")
@Getter @Setter
public class Department {

    @Id
    private String departmentId;

    @Column(length = 5, nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    public Department(String departmentId, String code, String name, String description) {
        this.departmentId = departmentId;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    protected Department(){}
}

