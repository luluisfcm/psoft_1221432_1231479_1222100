package com.example.psoft_1221432_1231479_1222100.auth.api;

import com.example.psoft_1221432_1231479_1222100.usermanagement.model.Role;
import com.example.psoft_1221432_1231479_1222100.usermanagement.model.User;
import com.example.psoft_1221432_1231479_1222100.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthHelper {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("No authenticated user found");
        }

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new SecurityException("User not found: " + authentication.getName()));
    }

    public Role getCurrentUserRole() {
        return getCurrentUser().getRole();
    }

    public boolean isAdmin() {
        return getCurrentUserRole() == Role.ADMIN;
    }

    public boolean isPatient() {
        return getCurrentUserRole() == Role.PATIENT;
    }
}
