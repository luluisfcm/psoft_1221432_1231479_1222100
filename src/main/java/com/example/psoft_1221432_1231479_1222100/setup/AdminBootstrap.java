package com.example.psoft_1221432_1231479_1222100.setup;

import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Role;
import com.example.psoft_1221432_1231479_1222100.usermanagement.model.User;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private int adminCounter = 1;

    @Override
    public void run(String... args) {
        createAdminIfNotExists("luis", "1221432");
        createAdminIfNotExists("francisco", "1222100");
        createAdminIfNotExists("jose", "1231479");
    }

    private void createAdminIfNotExists(String username, String rawPassword) {
        userRepository.findByUsername(username).orElseGet(() -> {
            User user = new User();
            String adminId = generateNextAdminId();
            user.setId(adminId);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(Role.ADMIN);
            return userRepository.save(user);
        });
    }

    private String generateNextAdminId() {
        String id = String.format("%02d", adminCounter);
        adminCounter++;
        return id;
    }
}

