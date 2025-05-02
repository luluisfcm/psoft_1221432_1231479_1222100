package org.example.repository;

import org.example.domain.Role;
import org.example.domain.User;

public interface UserRepository {
    boolean existsAnyAdmin();
    void save(User user);
}
