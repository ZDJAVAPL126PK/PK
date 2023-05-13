package com.sda.clinicapi.controller;

import com.sda.clinicapi.dto.DoctorDTO;
import com.sda.clinicapi.service.DoctorsService;
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
@Tag(name = "Doctors Controller")
@SecurityRequirement(name = "basicAuth")
@RequestMapping(DoctorsController.API_DOCTORS_PATH)
public class DoctorsController {

    public static final String API_DOCTORS_PATH = "/api/doctors";

    private final DoctorsService doctorsService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Method is being used to get list of all doctors.")
    public ResponseEntity<List<DoctorDTO>> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "500") int pageSize,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "id") String sortColumn) {

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, direction, sortColumn);
        return ResponseEntity.ok(doctorsService.findAll(pageRequest));
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Method is being used to get doctor with given id.")
    public ResponseEntity<DoctorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorsService.findById(id));
    }

    // TODO: Security for resource owner!

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Method is being used to update existing doctor.")
    public void update(@Valid @RequestBody DoctorDTO doctorDTO, @PathVariable Long id) {
        doctorsService.update(id, doctorDTO);
    }
}
