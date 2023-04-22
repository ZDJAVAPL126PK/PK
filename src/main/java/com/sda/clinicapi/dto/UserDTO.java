package com.sda.clinicapi.dto;

import com.sda.clinicapi.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
@Builder
public class UserDTO {

//    @NotNull
//    @NotBlank
//    @NotEmpty
//    @PastOrPresent
//    @FutureOrPresent
//    @Pattern(regexp = "\\w{4,20}")

    Role role;

    @NotBlank(message = "Email cannot be blank!")
    String email;

    @NotBlank(message = "Username cannot be blank!")
    String username;

    @Size(min = 4, message = "Password too short (min = 4).")
    @NotBlank(message = "Password cannot be blank!")
    String password;

    boolean enabled;
}
