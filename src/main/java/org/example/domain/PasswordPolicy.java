package org.example.domain;

public class PasswordPolicy {
        public static boolean isValid(String password) {
        return password != null && password.length() >= 8;
    }
}
