package org.example.repository;

import org.example.domain.Appointment;

import java.util.*;

public interface AppointmentRepository {
    void save(Appointment appointment);
    Optional<Appointment> findById(UUID id);
    List<Appointment> findByPatientId(UUID patientId);
    void deleteById(UUID id);
    boolean exists(UUID id);
    List<Appointment> findAll();
}
