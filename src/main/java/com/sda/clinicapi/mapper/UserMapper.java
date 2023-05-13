package com.sda.clinicapi.mapper;

import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO map(User user);

    User map(UserDTO userDTO);
}
