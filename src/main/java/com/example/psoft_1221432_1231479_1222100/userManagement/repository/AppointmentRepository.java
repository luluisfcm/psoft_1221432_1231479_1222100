package com.example.psoft_1221432_1231479_1222100.userManagement.repository;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    // Buscar consultas por paciente
    List<Appointment> findByPatient_Id(String patientId);

    // Buscar consultas por médico
    List<Appointment> findByPhysician_Id(String physicianId);

    // Buscar por estado
    List<Appointment> findByStatus(String status);

    boolean existsByPhysician_IdAndDateAndTime(String physicianId, LocalDate date, String time);

}
