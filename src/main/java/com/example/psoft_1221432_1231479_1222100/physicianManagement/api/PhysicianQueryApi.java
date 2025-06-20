package com.example.psoft_1221432_1231479_1222100.physicianManagement.api;

import com.example.psoft_1221432_1231479_1222100.auth.api.AuthHelper;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.AvailableSlotResponse;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.PhysicianDetailsPatientResponse;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.service.PhysicianService;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/physicians")
@RequiredArgsConstructor
@Tag(name = "Consulta de Médicos", description = "Endpoints de consulta e pesquisa de médicos acessíveis a pacientes ou outros papéis conforme regras de acesso")
public class PhysicianQueryApi {

    private final PhysicianService physicianService;
    private final AuthHelper authHelper;

    @Operation(summary = "Obter detalhes de um médico", description = "Devolve os detalhes de um médico dependendo do papel do utilizador autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do médico devolvidos com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content)
    })
    @GetMapping("/{physicianId}")
    public ResponseEntity<?> getPhysician(
            @Parameter(description = "ID do médico") @PathVariable String physicianId) {
        Role role = authHelper.getCurrentUser().getRole();
        Object response = physicianService.getByIdForRole(physicianId, role);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Pesquisar médicos", description = "Pesquisa médicos com base no nome e/ou especialidade. Apenas acessível a pacientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de médicos encontrada",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PhysicianDetailsPatientResponse.class)))),
            @ApiResponse(responseCode = "403", description = "Acesso negado para perfis não autorizados", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PhysicianDetailsPatientResponse>> searchPhysicians(
            @Parameter(description = "Nome parcial ou completo do médico") @RequestParam(required = false) String name,
            @Parameter(description = "Especialidade médica") @RequestParam(required = false) String specialty) {

        if (!authHelper.isPatient()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<PhysicianDetailsPatientResponse> results = physicianService.search(name, specialty);
        return ResponseEntity.ok(results);
    }
    @GetMapping("/{physicianId}/available-slots")
    public ResponseEntity<AvailableSlotResponse> getAvailableSlots(@PathVariable String physicianId) {
        if (!authHelper.isPatient()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
       }
        return ResponseEntity.ok(physicianService.getAvailableSlots(physicianId));
    }
}
