package com.sda.clinicapi.mapper;

import com.sda.clinicapi.dto.DoctorDTO;
import com.sda.clinicapi.dto.DoctorSignupDTO;
import com.sda.clinicapi.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DoctorsSignupMapper {

    DoctorsSignupMapper INSTANCE = Mappers.getMapper(DoctorsSignupMapper.class);

    DoctorDTO mapDoctorDTO(DoctorSignupDTO doctorSignupDTO);

    UserDTO mapUserDTO(DoctorSignupDTO doctorSignupDTO);
}
