package com.sda.clinicapi.service;


import com.sda.clinicapi.dto.*;
import com.sda.clinicapi.enums.Role;
import com.sda.clinicapi.mapper.DoctorsSignupMapper;
import com.sda.clinicapi.mapper.PatientsSignupMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UsersService usersService;
    private final EmailsService emailsService;

    private final DoctorsService doctorsService;
    private final DoctorsSignupMapper doctorsSignupMapper;

    private final PatientsService patientsService;
    private final PatientsSignupMapper patientsSignupMapper;

    private final ConfirmationCodesService confirmationCodesService;

    @Transactional
    public void confirmEmail(String username, String confirmationCode) {
        confirmationCodesService.validate(confirmationCode);
        usersService.enable(username);
        confirmationCodesService.deleteByCode(confirmationCode);
    }

    @Transactional
    public void signupDoctor(DoctorSignupDTO signupDTO) {
        UserDTO userDTO = doctorsSignupMapper.mapUserDTO(signupDTO);
        persistUser(userDTO, Role.ROLE_DOCTOR);

        DoctorDTO doctor = doctorsSignupMapper.mapDoctorDTO(signupDTO);
        doctorsService.create(doctor);

        sendEmail(userDTO);
    }

    @Transactional
    public void signupPatient(PatientSignupDTO signupDTO) {
        UserDTO userDTO = patientsSignupMapper.mapUserDTO(signupDTO);
        persistUser(userDTO, Role.ROLE_PATIENT);

        PatientDTO patient = patientsSignupMapper.mapPatientDTO(signupDTO);
        patientsService.create(patient);

        sendEmail(userDTO);
    }

    private void persistUser(UserDTO userDTO, Role role) {
        userDTO.setEnabled(false);
        userDTO.setRole(role);
        usersService.create(userDTO);
    }

    private void sendEmail(UserDTO userDTO) {
        String confirmationCode = confirmationCodesService.generateCode();
        String confirmationLink = generateLink(userDTO, confirmationCode);
        CompletableFuture.runAsync(() -> emailsService.sendConfirmationCode(userDTO.getEmail(), confirmationLink));
    }

    private String generateLink(UserDTO userDTO, String confirmationCode) {
        return "http://localhost:8080/api/signup/confirm-email?username=%s&code=%s"
                .formatted(userDTO.getUsername(), confirmationCode);
    }
}
