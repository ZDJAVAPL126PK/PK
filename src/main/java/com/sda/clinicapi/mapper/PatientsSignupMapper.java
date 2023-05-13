package com.sda.clinicapi.mapper;

import com.sda.clinicapi.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientsSignupMapper {

    PatientsSignupMapper INSTANCE = Mappers.getMapper(PatientsSignupMapper.class);

    PatientDTO mapPatientDTO(PatientSignupDTO patientSignupDTO);

    UserDTO mapUserDTO(PatientSignupDTO patientSignupDTO);
}
