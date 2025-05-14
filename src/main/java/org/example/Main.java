package org.example;

import org.example.controller.PhysicianConsoleController;
import org.example.repository.InMemoryPhysicianRepository;
import org.example.repository.PhysicianRepository;
import org.example.service.PhysicianRegistrationService;

public class Main {
    public static void main(String[] args) {
        PhysicianRepository physicianRepository = new InMemoryPhysicianRepository();
        PhysicianRegistrationService registrationService = new PhysicianRegistrationService(physicianRepository);
        PhysicianConsoleController controller = new PhysicianConsoleController(registrationService, physicianRepository);

        controller.run();
    }
}