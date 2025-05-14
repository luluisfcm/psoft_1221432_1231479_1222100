package org.example.audit;

public class ConsoleAuditLogger implements AuditLogger {
    @Override
    public void log(String message) {
        System.out.println("Audit log: " + message);
    }
}
