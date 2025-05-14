package org.example.security;

public class SimplePasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String rawPassword) {
        return "ENCODED_" + rawPassword;
    }
}
