package com.example.psoft_1221432_1231479_1222100.physicianManagement.api;

import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.PhysicianIdResponse;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.RegisterPhysicianRequest;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.service.PhysicianService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/physicians")
@RequiredArgsConstructor
@Tag(name = "Gestão de Médicos", description = "Endpoints para administração e registo de médicos")
public class PhysicianApi {

    private final PhysicianService physicianService;

    @Operation(summary = "Registar novo médico", description = "Cria um novo registo de médico e devolve o respetivo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Médico registado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhysicianIdResponse.class))),
            @ApiResponse(responseCode = "400", description = "Pedido inválido", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<PhysicianIdResponse> register(
            @RequestBody @Valid RegisterPhysicianRequest request) {
        PhysicianIdResponse response = physicianService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
