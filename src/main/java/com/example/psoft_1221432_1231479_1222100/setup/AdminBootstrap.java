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

    private int adminCounter = 1; // contador para gerar IDs: ADM01, ADM02...

    @Override
    public void run(String... args) {
        createAdminIfNotExists("luis", "1230773");
    }

    private void createAdminIfNotExists(String email, String rawPassword) {
        userRepository.findByUsername(email).orElseGet(() -> {
            User user = new User();
            String adminId = generateNextAdminId();
            user.setId(adminId);
            user.setUsername(email);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(Role.ADMIN);
            return userRepository.save(user);
        });
    }

    private String generateNextAdminId() {
        String id = String.format("ADM%02d", adminCounter);
        adminCounter++;
        return id;
    }
}

