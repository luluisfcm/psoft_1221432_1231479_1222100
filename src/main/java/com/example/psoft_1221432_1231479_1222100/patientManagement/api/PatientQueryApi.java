package com.example.psoft_1221432_1231479_1222100.patientManagement.api;

import com.example.psoft_1221432_1231479_1222100.patientManagement.dto.PatientDetailsResponse;
import com.example.psoft_1221432_1231479_1222100.patientManagement.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/patients")
@RequiredArgsConstructor
@Tag(name = "Consulta de Pacientes", description = "Endpoint para pesquisa de pacientes por administradores")
public class PatientQueryApi {

    private final PatientService patientService;

    @Operation(summary = "Pesquisar pacientes", description = "Pesquisa pacientes pelo nome (ou devolve todos se o nome for omitido)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes encontrada",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatientDetailsResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<PatientDetailsResponse>> searchPatients(
            @Parameter(description = "Nome parcial ou completo do paciente para filtrar resultados")
            @RequestParam(required = false) String name) {
        List<PatientDetailsResponse> results = patientService.searchByName(name);
        return ResponseEntity.ok(results);
    }
}
