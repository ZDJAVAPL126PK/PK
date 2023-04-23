package com.sda.clinicapi.mapper;

import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.model.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    public UserDTO map(User user) {

        if (Objects.isNull(user)) {
            return UserDTO.builder().build();
        }

        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }

    public User map(UserDTO userDTO) {

        if (Objects.isNull(userDTO)) {
            return new User();
        }

        return User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .enabled(userDTO.isEnabled())
                .build();
    }
}
