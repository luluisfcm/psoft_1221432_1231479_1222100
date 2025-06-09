package com.example.psoft_1221432_1231479_1222100.appointmentManagement.services;

import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.*;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.*;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.AppointmentRepository;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.PatientRepository;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.PhysicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PhysicianRepository physicianRepository;
    private final PatientRepository patientRepository;

    public AppointmentIdResponse schedule(ScheduleAppointmentRequest request) {
        validateAppointmentTime(request.getDate(), request.getTime());

        // Verificar se já existe marcação para este médico nesse horário
        boolean slotOcupado = appointmentRepository.existsByPhysician_IdAndDateAndTime(
                request.getPhysicianId(),
                request.getDate(),
                request.getTime()
        );

        if (slotOcupado) {
            throw new IllegalArgumentException("This time slot is already booked for the selected physician.");
        }

        Physician physician = physicianRepository.findById(request.getPhysicianId())
                .orElseThrow(() -> new IllegalArgumentException("Physician not found"));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(UUID.randomUUID().toString().substring(0, 8));
        appointment.setPhysician(physician);
        appointment.setPatient(patient);
        appointment.setDate(request.getDate());
        appointment.setTime(request.getTime());
        appointment.setConsultationType(request.getConsultationType());
        appointment.setStatus("SCHEDULED");

        appointmentRepository.save(appointment);

        return new AppointmentIdResponse(appointment.getAppointmentId());
    }

    private void validateAppointmentTime(LocalDate date, String timeStr) {
        LocalTime time;
        try {
            time = LocalTime.parse(timeStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Use HH:mm");
        }

        DayOfWeek day = date.getDayOfWeek();

        boolean isWeekday = day.getValue() >= 1 && day.getValue() <= 5; // Mon-Fri
        boolean isSaturday = day == DayOfWeek.SATURDAY;

        // Clínica fechada aos domingos
        if (day == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Appointments cannot be scheduled on Sundays");
        }

        // Verifica se horário está dentro do permitido
        boolean validSlot = false;
        if (isWeekday) {
            validSlot = (isInRange(time, "09:00", "13:00") || isInRange(time, "14:00", "20:00"));
        } else if (isSaturday) {
            validSlot = isInRange(time, "09:00", "13:00");
        }

        if (!validSlot) {
            throw new IllegalArgumentException("Time is outside clinic hours");
        }

        // Verifica se é múltiplo de 20 minutos
        if (time.getMinute() % 20 != 0) {
            throw new IllegalArgumentException("Appointments must start on 20-minute intervals (e.g., 09:00, 09:20)");
        }
    }

    private boolean isInRange(LocalTime time, String start, String end) {
        LocalTime startTime = LocalTime.parse(start);
        LocalTime endTime = LocalTime.parse(end);
        return !time.isBefore(startTime) && time.isBefore(endTime);
    }

    public List<AppointmentViewResponse> getAppointmentsForPatient(String patientId) {
        return appointmentRepository.findByPatient_Id(patientId)
                .stream()
                .map(a -> AppointmentViewResponse.builder()
                        .appointmentId(a.getAppointmentId())
                        .physicianName(a.getPhysician().getName())
                        .date(a.getDate().toString())
                        .time(a.getTime())
                        .consultationType(a.getConsultationType())
                        .status(a.getStatus())
                        .build())
                .toList();
    }

    public void updateAppointment(String appointmentId, AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.update(request.getTime(), request.getDate(), request.getConsultationType());
        appointmentRepository.save(appointment);
    }

    public void cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.cancel();
        appointmentRepository.save(appointment);
    }

    public AppointmentViewResponse updateAndReturn(String appointmentId, AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.update(request.getTime(), request.getDate(), request.getConsultationType());
        appointmentRepository.save(appointment);

        return toViewResponse(appointment);
    }

    public AppointmentViewResponse cancelAndReturn(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.cancel();
        appointmentRepository.save(appointment);

        return toViewResponse(appointment);
    }

    private AppointmentViewResponse toViewResponse(Appointment a) {
        return AppointmentViewResponse.builder()
                .appointmentId(a.getAppointmentId())
                .physicianName(a.getPhysician().getName())
                .date(a.getDate().toString())
                .time(a.getTime())
                .consultationType(a.getConsultationType())
                .status(a.getStatus())
                .build();
    }

    public AppointmentViewResponse getAppointmentById(String id, User currentUser) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        // Se for paciente, só pode ver se for a própria consulta
        if (currentUser.getRole() == Role.PATIENT &&
                !appointment.getPatient().getId().equals(currentUser.getId())) {
            throw new SecurityException("You are not authorized to view this appointment");
        }

        return toViewResponse(appointment);
    }

    public List<TopPhysicianResponse> getTop5Physicians() {
        List<Appointment> appointments = appointmentRepository.findAll();

        return appointments.stream()
                .collect(Collectors.groupingBy(
                        Appointment::getPhysician,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(5)
                .map(entry -> new TopPhysicianResponse(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getValue()
                ))
                .toList();
    }


}
