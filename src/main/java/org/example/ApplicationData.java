package org.example;

import org.example.repository.InMemoryUserRepository;
import org.example.repository.UserRepository;

public class ApplicationData {
    public static final UserRepository userRepository = new InMemoryUserRepository();
}
