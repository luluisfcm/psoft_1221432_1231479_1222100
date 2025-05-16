package com.example.psoft_1221432_1231479_1222100.usermanagement.service;
import com.example.psoft_1221432_1231479_1222100.usermanagement.dto.RegisterUserRequest;
import com.example.psoft_1221432_1231479_1222100.usermanagement.dto.UserIdResponse;
import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Role;
import com.example.psoft_1221432_1231479_1222100.usermanagement.model.User;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserIdResponse register(RegisterUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString().substring(0, 5));
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));

        User savedUser = userRepository.save(user);

        return UserIdResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole().name())
                .build();
    }
}