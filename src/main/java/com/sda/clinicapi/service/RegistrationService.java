package com.sda.clinicapi.service;


import com.sda.clinicapi.dto.*;
import com.sda.clinicapi.enums.Role;
import com.sda.clinicapi.mapper.DoctorsSignupMapper;
import com.sda.clinicapi.mapper.PatientsSignupMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UsersService usersService;

    private final DoctorsService doctorsService;
    private final DoctorsSignupMapper doctorsSignupMapper;

    private final PatientsService patientsService;
    private final PatientsSignupMapper patientsSignupMapper;

    @Transactional
    public void signupDoctor(DoctorSignupDTO signupDTO) {
        UserDTO userDTO = doctorsSignupMapper.mapUserDTO(signupDTO);
        persistUser(userDTO, Role.ROLE_DOCTOR);

        DoctorDTO doctor = doctorsSignupMapper.mapDoctorDTO(signupDTO);
        doctorsService.create(doctor);
    }

    @Transactional
    public void signupPatient(PatientSignupDTO signupDTO) {
        UserDTO userDTO = patientsSignupMapper.mapUserDTO(signupDTO);
        persistUser(userDTO, Role.ROLE_PATIENT);

        PatientDTO patient = patientsSignupMapper.mapPatientDTO(signupDTO);
        patientsService.create(patient);
    }

    private void persistUser(UserDTO userDTO, Role role) {
        userDTO.setEnabled(false);
        userDTO.setRole(role);
        usersService.create(userDTO);
    }
}
