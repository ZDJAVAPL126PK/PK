package com.sda.clinicapi.dto;

import com.sda.clinicapi.enums.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {

    Role role;
    String email;
    String username;
    String password;
    boolean enabled;
}
