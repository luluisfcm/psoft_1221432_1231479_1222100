package com.example.psoft_1221432_1231479_1222100.userManagement.repository;


import com.example.psoft_1221432_1231479_1222100.userManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
