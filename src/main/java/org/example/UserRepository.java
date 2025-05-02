package org.example;

public interface UserRepository {
    boolean existsAnyAdmin();
    void save(User user);
}
class InMemoryUserRepository implements UserRepository {
    private boolean adminExists = false;

    @Override
    public boolean existsAnyAdmin() {
        return adminExists;
    }

    @Override
    public void save(User user) {
        System.out.println("User saved: " + user.getUsername());
        if (user.getRole() == Role.ADMIN) {
            adminExists = true;
        }
    }
}