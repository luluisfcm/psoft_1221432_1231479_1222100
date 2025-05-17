package com.example.psoft_1221432_1231479_1222100.auth.api;
import com.example.psoft_1221432_1231479_1222100.auth.dto.LoginResponse;
import com.example.psoft_1221432_1231479_1222100.auth.services.AuthService;
import com.example.psoft_1221432_1231479_1222100.userManagement.dto.RegisterUserRequest;
import com.example.psoft_1221432_1231479_1222100.userManagement.dto.UserIdResponse;
import com.example.psoft_1221432_1231479_1222100.userManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authService.authenticate(
                    request.getUsername(),
                    request.getPassword()
            );

            String token = authService.generateToken(authentication);

            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(LoginResponse.builder()
                    .token(token)
                    .roles(roles)
                    .build());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid RegisterUserRequest request) {
        // Regista o utilizador
        userService.register(request);

        // Autentica logo após registo
        Authentication authentication = authService.authenticate(request.getUsername(), request.getPassword());

        // Gera token
        String token = authService.generateToken(authentication);

        // Extrai roles
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                LoginResponse.builder()
                        .token(token)
                        .roles(roles)
                        .build()
        );
    }
}
