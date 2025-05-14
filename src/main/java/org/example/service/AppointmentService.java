package org.example.service;

import org.example.domain.Appointment;
import org.example.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AppointmentService {
    private final AppointmentRepository repo;

    public AppointmentService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public Appointment schedule(UUID patientId, UUID physicianId, String date, String time, String type) {
        Appointment appointment = new Appointment(patientId, physicianId, date, time, type);
        repo.save(appointment);
        return appointment;
    }

    public List<Appointment> getByPatientId(UUID patientId) {
        return repo.findByPatientId(patientId);
    }

    public Optional<Appointment> getById(UUID id) {
        return repo.findById(id);
    }

    public void cancel(UUID id) {
        repo.deleteById(id);
    }

    public Appointment update(UUID id, String date, String time, String type) {
        Appointment existing = repo.findById(id).orElseThrow();
        existing.setDate(date);
        existing.setTime(time);
        existing.setConsultationType(type);
        repo.save(existing);
        return existing;
    }
}
