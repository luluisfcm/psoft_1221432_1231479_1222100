package org.example;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepository();
        PasswordEncoder passwordEncoder = new SimplePasswordEncoder();
        AuditLogger auditLogger = new ConsoleAuditLogger();
        DepartmentRepository departmentRepository = new InMemoryDepartmentRepository();
        SpecializationRepository specializationRepository = new InMemorySpecializationRepository();

        BootStrapService bootStrapService = new BootStrapService();
        bootStrapService.BootstrapService(userRepository, passwordEncoder, auditLogger);

        SystemSetup systemSetup = new SystemSetup(bootStrapService, departmentRepository, specializationRepository);

        try {
            systemSetup.bootstrapAdmins("admin", "StrongPassword123");
            systemSetup.bootstrapDepartments();
        } catch (Exception e) {
            System.out.println("Erro no setup: " + e.getMessage());
        }
    }
}
