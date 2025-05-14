package org.example.security;

public interface PasswordEncoder {
    String encode(String rawPassword);
}
