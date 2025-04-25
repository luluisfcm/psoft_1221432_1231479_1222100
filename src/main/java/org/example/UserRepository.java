package org.example;

public interface UserRepository {
    boolean existsAnyAdmin();
    void save(User user);
}
