package org.example;


class User {


    private String username;
    private String password;
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public Role getRole() { return role; }
}

    enum Role {
        ADMIN, USER
    }

    class PasswordPolicy {
        public static boolean isValid(String password) {
        return password != null && password.length() >= 8;
    }
}