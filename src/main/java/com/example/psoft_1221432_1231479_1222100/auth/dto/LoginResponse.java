package com.example.psoft_1221432_1231479_1222100.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private List<String> roles;
}
