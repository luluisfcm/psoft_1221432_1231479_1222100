package com.example.psoft_1221432_1231479_1222100.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Is required")
    private String username;
    @NotBlank(message = "Is required")
    private String password;
}