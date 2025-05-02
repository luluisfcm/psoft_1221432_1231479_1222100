package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface DepartmentRepository {
    void save(Department department);
    boolean existsAny();
    Department findById(UUID id);
    List<Department> findAll();
}

class InMemoryDepartmentRepository implements DepartmentRepository {
    private final List<Department> departments = new ArrayList<>();

    @Override
    public void save(Department department) {
        departments.add(department);
        System.out.println("Department saved: " + department.getName());
    }

    @Override
    public boolean existsAny() {
        return !departments.isEmpty();
    }

    @Override
    public Department findById(UUID id) {
        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + id));
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments);
    }
}
