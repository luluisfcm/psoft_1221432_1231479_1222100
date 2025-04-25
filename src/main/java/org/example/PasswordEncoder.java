package org.example;

public interface PasswordEncoder {
    String encode(String rawPassword);
}
class SimplePasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String rawPassword) {
        return "ENCODED_" + rawPassword;
    }
}