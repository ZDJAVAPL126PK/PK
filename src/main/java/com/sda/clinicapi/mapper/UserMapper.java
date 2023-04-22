package com.sda.clinicapi.mapper;

import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO map(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();

    }

    public User map(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .enabled(userDTO.isEnabled())
                .build();
    }
}
