package com.example.psoft_1221432_1231479_1222100.physicianManagement.api;

import com.example.psoft_1221432_1231479_1222100.physicianManagement.dto.*;
import com.example.psoft_1221432_1231479_1222100.physicianManagement.service.PhysicianService;
import com.example.psoft_1221432_1231479_1222100.userManagement.model.Physician;
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
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("/{id}")
    public ResponseEntity<Physician> updatePhysician(
            @PathVariable String id,
            @RequestBody PhysicianUpdateRequest request) {
        Physician updated = physicianService.updatePhysician(id, request);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable String id, @RequestParam("photo") MultipartFile photoFile) {
        physicianService.uploadPhoto(id, photoFile);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/register-base64")
    public ResponseEntity<Physician> registerWithPhotoBase64(
            @RequestBody RegisterPhysicianBase64DTO dto) {
        Physician physician = physicianService.registerWithPhotoBase64(dto);
        return ResponseEntity.ok(physician);
    }

}
