package com.sda.clinicapi.controller;

import com.sda.clinicapi.dto.DoctorSignupDTO;
import com.sda.clinicapi.dto.PatientSignupDTO;
import com.sda.clinicapi.service.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
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

}
