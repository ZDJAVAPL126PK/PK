package com.sda.clinicapi.dto;

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
public class PatientSignupDTO {

    @NotBlank(message = "Username cannot be blank!")
    String username;

    @Size(min = 4, message = "Password too short (min = 4).")
    @NotBlank(message = "Password cannot be blank!")
    String password;

    @Size(max = 50, message = "Max length exceeded (max = 50)!")
    @NotBlank(message = "Name cannot be blank!")
    String name;

    @Size(max = 50, message = "Max length exceeded (max = 50)!")
    @NotBlank(message = "Surname cannot be blank!")
    String surname;

    @Size(max = 12, message = "Max length exceeded (max = 12)!")
    @NotBlank(message = "Pesel cannot be blank!")
    String pesel;

    @NotBlank(message = "Email cannot be blank!")
    String email;

    @Size(max = 50, message = "Max length exceeded (max = 50)!")
    @NotBlank(message = "Phone number cannot be blank!")
    String phoneNumber;
}
