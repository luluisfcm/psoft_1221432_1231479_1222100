package org.example.repository;

import org.example.domain.Role;
import org.example.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public boolean existsAnyAdmin() {
        return users.stream().anyMatch(u -> u.getRole() == Role.ADMIN);
    }
}

