package org.example.repository;

import org.example.domain.Role;
import org.example.domain.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsAnyAdmin();
    void save(User user);
    Optional<User> findByUsername(String username);
}
