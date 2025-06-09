package com.example.psoft_1221432_1231479_1222100.physicianManagement.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvailableSlotResponse {
    private String physicianId;
    private String workingDays;
    private String workingHours;
    private List<String> availableSlots;
}


