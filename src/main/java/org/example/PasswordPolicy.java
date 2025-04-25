package org.example;

public class PasswordPolicy {

    public static boolean isValid(String password) {
        return password != null
                && password.length() >= 8
                // pelo menos uma letra maiúscula
                && password.matches(".*[A-Z].*")
                // pelo menos uma letra minúscula
                && password.matches(".*[a-z].*")
                // pelo menos um dígito
                && password.matches(".*\\d.*")
                // pelo menos um dos caracteres especiais !@#$%^&()
                && password.matches(".*[!@#$%^&()].*");
    }
}
