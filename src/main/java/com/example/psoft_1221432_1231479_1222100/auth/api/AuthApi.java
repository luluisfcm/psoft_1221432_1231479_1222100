package com.example.psoft_1221432_1231479_1222100.auth.api;

import com.example.psoft_1221432_1231479_1222100.auth.dto.LoginResponse;
import com.example.psoft_1221432_1231479_1222100.auth.services.AuthService;
import com.example.psoft_1221432_1231479_1222100.userManagement.dto.RegisterUserRequest;
import com.example.psoft_1221432_1231479_1222100.userManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticação", description = "Endpoints públicos para login e registo de utilizadores")
public class AuthApi {

    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "Iniciar sessão", description = "Permite a autenticação de um utilizador e devolve um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content)
    })
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

    @Operation(summary = "Registar novo utilizador", description = "Cria um novo utilizador e devolve um token JWT após autenticação automática")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registo e autenticação bem-sucedidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid RegisterUserRequest request) {
        userService.register(request);

        Authentication authentication = authService.authenticate(request.getUsername(), request.getPassword());

        String token = authService.generateToken(authentication);

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
