package org.example.bootstrapping;

import org.example.audit.AuditLogger;
import org.example.domain.PasswordPolicy;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BootStrapService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuditLogger auditLogger;

    public void BootstrapService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuditLogger auditLogger) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.auditLogger = auditLogger;
    }

    public void initializeAdmin(String username, String rawPassword) {
        if (userRepository.existsAnyAdmin()) {
            throw new IllegalStateException("Bootstrap already completed.");
        }

        if (!PasswordPolicy.isValid(rawPassword)) {
            throw new IllegalArgumentException("Password does not meet security requirements.");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User admin = new User(username, hashedPassword, Role.ADMIN);
        userRepository.save(admin);

        auditLogger.log("Initial system administrator created: " + username);
    }
}
