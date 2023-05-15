package com.sda.clinicapi.dto;

import com.sda.clinicapi.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

//    @NotNull
//    @NotBlank
//    @NotEmpty
//    @PastOrPresent
//    @FutureOrPresent
//    @Pattern(regexp = "\\w{4,20}")

    private Role role;

    @NotBlank(message = "Email cannot be blank!")
    private String email;

    @NotBlank(message = "Username cannot be blank!")
    private String username;

    @Size(min = 4, message = "Password too short (min = 4).")
    @NotBlank(message = "Password cannot be blank!")
    private String password;

    private boolean enabled;
}
