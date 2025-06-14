package com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto;
import lombok.Data;

@Data
public class AppointmentAgeGroupStats {
    private String ageGroup;
    private long count;

    public AppointmentAgeGroupStats(String ageGroup, long count) {
        this.ageGroup = ageGroup;
        this.count = count;
    }
}
