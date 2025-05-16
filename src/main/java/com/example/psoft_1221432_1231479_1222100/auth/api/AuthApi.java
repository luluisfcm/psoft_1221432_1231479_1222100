package com.example.psoft_1221432_1231479_1222100.auth.api;

import com.example.psoft_1221432_1231479_1222100.auth.dto.LoginRequest;
import com.example.psoft_1221432_1231479_1222100.auth.dto.LoginResponse;
import com.example.psoft_1221432_1231479_1222100.auth.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class AuthApi {

    private static final Logger logger = LoggerFactory.getLogger(AuthApi.class);
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        try {
            Authentication authentication = authService.authenticate(request.getUsername(), request.getPassword());
            String token = authService.generateToken(authentication);

            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            logger.debug("User authorities: {}", roles);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(LoginResponse.builder()
                            .token(token)
                            .roles(roles)
                            .build());
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", request.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
