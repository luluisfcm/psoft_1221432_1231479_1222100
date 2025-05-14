package org.example.repository;

import org.example.domain.Appointment;

import java.util.*;

public class InMemoryAppointmentRepository implements AppointmentRepository {
    private final Map<UUID, Appointment> store = new HashMap<>();

    public void save(Appointment appointment) {
        store.put(appointment.getId(), appointment);
    }

    public Optional<Appointment> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Appointment> findByPatientId(UUID patientId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : store.values()) {
            if (a.getPatientId().equals(patientId)) {
                result.add(a);
            }
        }
        return result;
    }

    public void deleteById(UUID id) {
        store.remove(id);
    }

    public boolean exists(UUID id) {
        return store.containsKey(id);
    }

    public List<Appointment> findAll() {
        return new ArrayList<>(store.values());
    }
}
