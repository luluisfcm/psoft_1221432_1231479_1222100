package com.example.psoft_1221432_1231479_1222100.usermanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserIdResponse {
    private String id;
    private String username;
    private String role;
}