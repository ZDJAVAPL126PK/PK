package com.sda.clinicapi.mapper;

import com.sda.clinicapi.dto.PatientDTO;
import com.sda.clinicapi.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientsMapper {

    PatientsMapper INSTANCE = Mappers.getMapper(PatientsMapper.class);

    @Mapping(source = "user.username", target = "username")
    PatientDTO map(Patient patient);

    @Mapping(source = "username", target = "user.username")
    Patient map(PatientDTO patientDTO);
}
