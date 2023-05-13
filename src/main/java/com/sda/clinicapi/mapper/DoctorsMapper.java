package com.sda.clinicapi.mapper;

import com.sda.clinicapi.dto.DoctorDTO;
import com.sda.clinicapi.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
//@Mapper(componentModel = "spring") - jeżeli nie jest zdefiniowany w pom.xml
public interface DoctorsMapper {

    DoctorsMapper INSTANCE = Mappers.getMapper(DoctorsMapper.class);

    @Mapping(source = "user.username", target = "username")
    DoctorDTO map(Doctor doctor);

    @Mapping(source = "username", target = "user.username")
    Doctor map(DoctorDTO doctorDTO);
}
