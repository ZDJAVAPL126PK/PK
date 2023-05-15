package com.sda.clinicapi.controller;

import com.sda.clinicapi.dto.DoctorSignupDTO;
import com.sda.clinicapi.dto.PatientSignupDTO;
import com.sda.clinicapi.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Registration Controller")
@RequestMapping(RegistrationController.API_REGISTRATION_PATH)
public class RegistrationController {

    public static final String API_REGISTRATION_PATH = "/api/signup";

    private final RegistrationService registrationService;

    @PostMapping("/doctor")
    @ResponseStatus(HttpStatus.CREATED)
    public void signupDoctor(@Valid @RequestBody DoctorSignupDTO doctorSignupDTO) {
        registrationService.signupDoctor(doctorSignupDTO);
    }

    @PostMapping("/patient")
    @ResponseStatus(HttpStatus.CREATED)
    public void signupPatient(@Valid @RequestBody PatientSignupDTO patientSignupDTO) {
        registrationService.signupPatient(patientSignupDTO);
    }

    @GetMapping("/confirm-email")
    @Operation(summary = "Method is being used to confirm user email.")
    public ResponseEntity<String> confirmEmail(@RequestParam String username, @RequestParam String code) {
        registrationService.confirmEmail(username, code);
        return ResponseEntity.ok("Email confirmed!");
    }

}
