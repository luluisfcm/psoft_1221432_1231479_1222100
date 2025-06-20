package com.example.psoft_1221432_1231479_1222100.patientManagement.api;

import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.AppointmentHistoryResponse;
import com.example.psoft_1221432_1231479_1222100.appointmentManagement.dto.ScheduleAppointmentRequest;
import com.example.psoft_1221432_1231479_1222100.auth.api.AuthHelper;
import com.example.psoft_1221432_1231479_1222100.patientManagement.dto.PatientDetailsResponse;
import com.example.psoft_1221432_1231479_1222100.patientManagement.dto.RegisterPatientRequest;
import com.example.psoft_1221432_1231479_1222100.patientManagement.dto.PatientIdResponse;
import com.example.psoft_1221432_1231479_1222100.patientManagement.dto.UpdatePatientRequest;
import com.example.psoft_1221432_1231479_1222100.patientManagement.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/public/patients")
@RequiredArgsConstructor
@Tag(name = "Gestão de Pacientes", description = "Endpoints públicos para registo e consulta de pacientes")
public class PatientApi {
    private final AuthHelper authHelper;
    private final PatientService patientService;

    @Operation(summary = "Registar novo paciente", description = "Regista um paciente anónimo e devolve o ID gerado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente registado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientIdResponse.class))),
            @ApiResponse(responseCode = "400", description = "Pedido inválido", content = @Content)
    })
//    @PostMapping("/register")
//    public ResponseEntity<PatientIdResponse> registerPatient(
//            @RequestBody @Valid RegisterPatientRequest request) {
//        PatientIdResponse response = patientService.register(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

        @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<PatientIdResponse> registerPatient(
            @RequestPart("data") String data,
            @RequestPart(value = "photo", required = false) MultipartFile photoFile) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            RegisterPatientRequest request = mapper.readValue(data, RegisterPatientRequest.class);

            // DEBUG: Mostra no log o que foi lido
            System.out.println("REGISTO: " + request.getName() + " | " + request.getEmail() + " | " + request.getDob());

            PatientIdResponse response = patientService.register(request, photoFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @Operation(summary = "Obter detalhes de paciente", description = "Devolve os dados completos de um paciente através do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDetailsResponse.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailsResponse> getById(@PathVariable String id) {
        PatientDetailsResponse response = patientService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDetailsResponse> updatePatient(
//        if //(!authHelper.isPatient()) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
            @PathVariable String id,
            @RequestBody UpdatePatientRequest request) {
        return ResponseEntity.ok(patientService.update(id, request));
    }
    @PostMapping("/appointments")
    public ResponseEntity<Void> scheduleAppointment(
        @RequestBody @Valid ScheduleAppointmentRequest request) {
        if (!authHelper.isPatient() && !authHelper.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        patientService.scheduleAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<AppointmentHistoryResponse>> getHistory(@PathVariable String id) {
        if (!authHelper.isPatient() && !authHelper.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(patientService.getAppointmentHistory(id));
    }


}
