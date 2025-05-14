package org.example.repository;

import org.example.domain.Role;
import org.example.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {

    private final Map<UUID, User> users = new HashMap<>();
    private boolean adminExists = false;

    @Override
    public boolean existsAnyAdmin() {
        return adminExists;
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        users.put(user.getId(), user);
        System.out.println("User saved: " + user.getUsername());
        if (user.getRole() == Role.ADMIN) {
            adminExists = true;
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAllByRole(String role) {
        return users.values().stream()
                .filter(u -> u.getRole().name().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
