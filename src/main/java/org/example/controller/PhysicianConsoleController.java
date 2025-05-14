package org.example.controller;

import org.example.domain.Specialization;
import org.example.repository.PhysicianRepository;
import org.example.service.PhysicianRegistrationService;

import java.util.Scanner;
import java.util.UUID;

public class PhysicianConsoleController {
    private final PhysicianRegistrationService registrationService;
    private final PhysicianRepository repository;

    public PhysicianConsoleController(PhysicianRegistrationService registrationService,
                                      PhysicianRepository repository) {
        this.registrationService = registrationService;
        this.repository = repository;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String opcao;

        do {
            System.out.println("\n=== Menu de Physician ===");
            System.out.println("1. Registrar Physician");
            System.out.println("2. Ver Physician pelo ID");
            System.out.println("3. Listar Todos os Physicians");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> registerPhysician(scanner);
                case "2" -> viewById(scanner);
                case "3" -> listAllPhysicians();
                case "0" -> System.out.println("A sair...");
                default -> System.out.println("Opção inválida.");
            }

        } while (!opcao.equals("0"));
    }

    private void registerPhysician(Scanner scanner) {
        System.out.println("\n=== Registo do Physician ===");

        System.out.print("Nome: ");
        String name = scanner.nextLine();

        System.out.print("Contato: ");
        String contact = scanner.nextLine();

        System.out.print("Horário de trabalho: ");
        String hours = scanner.nextLine();

        System.out.print("Especialidade: ");
        String specName = scanner.nextLine();

        Specialization specialization = new Specialization(specName, UUID.randomUUID());
        registrationService.register(name, contact, hours, specialization);

        System.out.println("✅ Médico registrado com sucesso.");
    }

    private void viewById(Scanner scanner) {
        System.out.print("\nIntroduza o ID do physician: ");
        String input = scanner.nextLine();

        try {
            UUID id = UUID.fromString(input);
            var physician = repository.findById(id);

            if (physician != null) {
                System.out.println("\n=== Detalhes do Physician ===");
                System.out.println(physician);
            } else {
                System.out.println("Physician não encontrado com o ID fornecido.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Formato de ID inválido.");
        }
    }

    private void listAllPhysicians() {
        System.out.println("\n=== Lista de Physicians Registados ===");
        repository.findAll().forEach(System.out::println);
    }
}
