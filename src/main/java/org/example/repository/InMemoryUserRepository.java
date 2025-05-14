package org.example.repository;

import org.example.domain.Role;
import org.example.domain.User;

public class InMemoryUserRepository implements UserRepository {
    private boolean adminExists = false;

    @Override
    public boolean existsAnyAdmin() {
        return adminExists;
    }

    @Override
    public void save(User user) {
        System.out.println("User saved: " + user.getUsername());
        if (user.getRole() == Role.ADMIN) {
            adminExists = true;
        }
    }
}
