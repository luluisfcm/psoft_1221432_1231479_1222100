package com.example.psoft_1221432_1231479_1222100.physicianManagement.service;

import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.*;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Appointment;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Physician;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Role;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Specialty;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.AppointmentRepository;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.PhysicianRepository;
import com.example.psoft_1221432_1231479_1222100.userManagement.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhysicianService {
    @Value("${profile.photos.dir:profile_photos}")
    private String photosDir;
    @Autowired
    private final PhysicianRepository physicianRepository;
    @Autowired
    private final SpecialtyRepository specialtyRepository;
    @Autowired
    private AppointmentRepository appointmentRepository; //
    public PhysicianIdResponse register(RegisterPhysicianRequest request) {
        Specialty specialty = specialtyRepository.findById(request.getSpecialtyId())
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found"));

        Physician physician = new Physician();
        physician.setId(UUID.randomUUID().toString());
        physician.setName(request.getName());
        physician.setSpecialty(specialty);
        physician.setContactInfo(request.getContactInfo());
        physician.setWorkingHours(request.getWorkingHours());
        physician.setWorkingDays(request.getWorkingDays());

        Physician saved = physicianRepository.save(physician);

        return PhysicianIdResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .build();
    }

    public Object getByIdForRole(String id, Role role) {
        Physician physician = physicianRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Physician not found"));

        if (role == Role.ADMIN) {
            return PhysicianDetailsAdminResponse.builder()
                    .id(physician.getId())
                    .name(physician.getName())
                    .specialty(physician.getSpecialty().getName())
                    .contactInfo(physician.getContactInfo())
                    .workingHours(physician.getWorkingHours())
                    .workingDays(physician.getWorkingDays())
                    .build();
        } else {
            return PhysicianDetailsPatientResponse.builder()
                    .id(physician.getId())
                    .name(physician.getName())
                    .specialty(physician.getSpecialty().getName())
                    .build();
        }
    }

    public List<PhysicianDetailsPatientResponse> search(String name, String specialty) {
        List<Physician> results;

        if (name != null && specialty != null) {
            results = physicianRepository.findByNameContainingIgnoreCaseAndSpecialty_NameContainingIgnoreCase(name, specialty);
        } else if (name != null) {
            results = physicianRepository.findByNameContainingIgnoreCase(name);
        } else if (specialty != null) {
            results = physicianRepository.findBySpecialty_NameContainingIgnoreCase(specialty);
        } else {
            results = physicianRepository.findAll();
        }

        return results.stream().map(p ->
                PhysicianDetailsPatientResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .specialty(p.getSpecialty().getName())
                        .build()
        ).toList();
    }

    public Physician updatePhysician(String id, PhysicianUpdateRequest request) {
        Physician physician = physicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        if (request.getName() != null) physician.setName(request.getName());
        if (request.getContactInfo() != null) physician.setContactInfo(request.getContactInfo());
        if (request.getWorkingDays() != null) physician.setWorkingDays(request.getWorkingDays());
        if (request.getWorkingHours() != null) physician.setWorkingHours(request.getWorkingHours());

        if (request.getSpecialtyId() != null) {
            Specialty specialty = specialtyRepository.findById(request.getSpecialtyId())
                    .orElseThrow(() -> new RuntimeException("Specialty not found"));
            physician.setSpecialty(specialty);
        }

        return physicianRepository.save(physician);
    }

    public AvailableSlotResponse getAvailableSlots(String physicianId) {
        Physician physician = physicianRepository.findById(physicianId)
                .orElseThrow(() -> new IllegalArgumentException("Physician not found"));

        // Converte a string workingDays ("MON-FRI", "MONDAY,WEDNESDAY") para Set<DayOfWeek>
        Set<DayOfWeek> workingDays = parseWorkingDays(physician.getWorkingDays());

        // Parse horário: ex. "08:00-16:00"
        String[] hours = physician.getWorkingHours().split("-");
        LocalTime startHour = LocalTime.parse(hours[0]);
        LocalTime endHour = LocalTime.parse(hours[1]);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusWeeks(1);

        // Gera todos os slots possíveis de 20 em 20 minutos
        List<LocalDateTime> allSlots = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (workingDays.contains(date.getDayOfWeek())) {
                for (LocalTime time = startHour; time.isBefore(endHour); time = time.plusMinutes(20)) {
                    allSlots.add(LocalDateTime.of(date, time));
                }
            }
        }

        // Obtém marcações existentes
        List<Appointment> appointments = appointmentRepository.findByPhysicianIdAndDateBetween(
                physicianId, startDate, endDate
        );

        Set<LocalDateTime> bookedSlots = appointments.stream()
                .map(a -> LocalDateTime.of(a.getDate(), LocalTime.parse(a.getTime())))
                .collect(Collectors.toSet());

        // Filtra apenas os slots disponíveis
        List<String> availableSlots = allSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .map(LocalDateTime::toString)
                .toList();

        return AvailableSlotResponse.builder()
                .physicianId(physicianId)
                .workingDays(physician.getWorkingDays())  // continua como String
                .workingHours(physician.getWorkingHours())
                .availableSlots(availableSlots)
                .build();
    }

    private Set<DayOfWeek> parseWorkingDays(String workingDaysRaw) {
        Set<DayOfWeek> days = new HashSet<>();

        if (workingDaysRaw == null || workingDaysRaw.isBlank()) return days;

        for (String part : workingDaysRaw.toUpperCase().split(",")) {
            part = part.trim();
            switch (part) {
                case "MON-FRI" -> days.addAll(List.of(
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY,
                        DayOfWeek.FRIDAY
                ));
                case "SAT-SUN" -> days.addAll(List.of(
                        DayOfWeek.SATURDAY,
                        DayOfWeek.SUNDAY
                ));
                default -> days.add(DayOfWeek.valueOf(part));
            }
        }

        return days;
    }

    public void uploadPhoto(String physicianId, MultipartFile photoFile) {
        Physician physician = physicianRepository.findById(physicianId)
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        String fileName = "physician-" + System.currentTimeMillis() + "-" + photoFile.getOriginalFilename();
        File dest = new File("profile_photos", fileName);
        dest.getParentFile().mkdirs();
        try {
            photoFile.transferTo(dest);
            physician.setProfilePhotoPath(dest.getAbsolutePath());
            physicianRepository.save(physician);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao guardar foto de perfil", e);
        }
    }
    public Physician registerWithPhotoBase64(RegisterPhysicianBase64DTO dto) {
        Physician physician = new Physician();
        physician.setId(UUID.randomUUID().toString());
        physician.setName(dto.getName());
        physician.setSpecialty(dto.getSpecialtyId());
        physician.setContactInfo(dto.getContactInfo());
        physician.setWorkingHours(dto.getWorkingHours());
        physician.setWorkingDays(dto.getWorkingDays());

        if (dto.getPhotoBase64() != null && !dto.getPhotoBase64().isEmpty()) {
            try {
                // Limpa o prefixo se vier (data:image/png;base64,)
                String base64 = dto.getPhotoBase64();
                if (base64.contains(",")) {
                    base64 = base64.substring(base64.indexOf(",") + 1);
                }
                byte[] decoded = Base64.getDecoder().decode(base64);
                String fileName = "physician-" + System.currentTimeMillis() + ".jpg";
                File dest = new File("profile_photos", fileName);
                dest.getParentFile().mkdirs();
                try (FileOutputStream fos = new FileOutputStream(dest)) {
                    fos.write(decoded);
                }
                physician.setProfilePhotoPath(dest.getAbsolutePath());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao guardar foto Base64", e);
            }
        } else {
            // Usa placeholder se não vier foto
            File defaultFile = new File("profile_photos", "placeholder.jpg");
            physician.setProfilePhotoPath(defaultFile.getAbsolutePath());
        }

        return physicianRepository.save(physician);
    }

}