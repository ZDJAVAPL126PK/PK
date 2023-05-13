package com.sda.clinicapi.controller;

import com.sda.clinicapi.dto.PatientDTO;
import com.sda.clinicapi.service.PatientsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "Patients Controller")
@SecurityRequirement(name = "basicAuth")
@RequestMapping(PatientsController.API_PATIENTS_PATH)
public class PatientsController {

    public static final String API_PATIENTS_PATH = "/api/patients";

    private final PatientsService patientsService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Method is being used to get list of all patients.")
    public ResponseEntity<List<PatientDTO>> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "500") int pageSize,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "id") String sortColumn) {

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, direction, sortColumn);
        return ResponseEntity.ok(patientsService.findAll(pageRequest));
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Method is being used to get patient with given id.")
    public ResponseEntity<PatientDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(patientsService.findById(id));
    }

    // TODO: Security for resource owner!

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Method is being used to update existing patient.")
    public void update(@Valid @RequestBody PatientDTO patientDTO, @PathVariable Long id) {
        patientsService.update(id, patientDTO);
    }
}
