package com.sda.clinicapi;

import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.enums.Role;
import com.sda.clinicapi.model.User;

import java.util.UUID;

public class TestUtils {

    public static User createUser() {
        return createUser(UUID.randomUUID().toString());
    }

    public static User createUser(String username) {
        return User.builder()
                .username(username)
                .email("user@mail.com")
                .password("pass")
                .role(Role.ROLE_PATIENT)
                .enabled(true)
                .build();
    }

    public static UserDTO createUserDTO() {
        return createUserDTO(UUID.randomUUID().toString());
    }

    public static UserDTO createUserDTO(String username) {
        return UserDTO.builder()
                .email("userDTO@mail.com")
                .username(username)
                .password("pass")
                .role(Role.ROLE_PATIENT)
                .enabled(true)
                .build();

    }

}
