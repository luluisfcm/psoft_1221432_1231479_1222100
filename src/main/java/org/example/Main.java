package org.example;

public class Main {
    public static void main(String[] args) {
        // Instanciando as dependências
        UserRepository userRepository = new InMemoryUserRepository();
        PasswordEncoder passwordEncoder = new SimplePasswordEncoder();
        AuditLogger auditLogger = new ConsoleAuditLogger();

        // Criando o serviço e injetando as dependências
        BootStrapService bootStrapService = new BootStrapService();
        bootStrapService.BootstrapService(userRepository, passwordEncoder, auditLogger);

        // Inicializando o admin
        try {
            bootStrapService.initializeAdmin("admin", "StrongPassword123");
        } catch (Exception e) {
            System.out.println("Erro ao inicializar admin: " + e.getMessage());
        }
    }
}