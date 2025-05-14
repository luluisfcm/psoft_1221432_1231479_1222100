package org.example.repository;

import org.example.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    boolean existsAnyAdmin();
    void save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findById(UUID id);
    List<User> findAllByRole(String role);
    List<User> findAll();
}
