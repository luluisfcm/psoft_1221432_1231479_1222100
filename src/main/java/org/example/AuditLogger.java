package org.example;

public interface AuditLogger {
    void log(String message);
}
class ConsoleAuditLogger implements AuditLogger {
    @Override
    public void log(String message) {
        System.out.println("Audit log: " + message);
    }
}