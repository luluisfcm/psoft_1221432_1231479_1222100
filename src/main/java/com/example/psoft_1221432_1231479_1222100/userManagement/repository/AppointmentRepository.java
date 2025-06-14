package com.example.psoft_1221432_1231479_1222100.userManagement.repository;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    // Buscar consultas por paciente
    List<Appointment> findByPatient_Id(String patientId);

    boolean existsByPhysician_IdAndDateAndTime(String physicianId, LocalDate date, String time);

    @Query("SELECT a FROM Appointment a WHERE a.physician.id = :physicianId AND a.date BETWEEN :startDate AND :endDate")
    List<Appointment> findByPhysicianIdAndDateBetween(
            @Param("physicianId") String physicianId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    boolean existsByPhysicianIdAndDateAndTime(String physicianId, LocalDate date, String time);
    List<Appointment> findByPatientIdOrderByDateDesc(String patientId);

    List<Appointment> findByStatusAndDateGreaterThanEqualOrderByDateAscTimeAsc(String status, LocalDate date);

    List<Appointment> findAllByStatus(String status);

    long countByDateBetween(LocalDate start, LocalDate end);

    // Total de RESCHEDULED no mês
    long countByStatusAndDateBetween(String status, LocalDate start, LocalDate end);

}
