package com.sda.clinicapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    Long id;
    String title;

    @Size(max = 50, message = "Max length exceeded (max = 50)!")
    @NotBlank(message = "Name cannot be blank!")
    String name;

    @Size(max = 50, message = "Max length exceeded (max = 50)!")
    @NotBlank(message = "Surname cannot be blank!")
    String surname;

    String description;

    @Size(max = 50, message = "Max length exceeded (max = 50)!")
    @NotBlank(message = "Phone number cannot be blank!")
    String phoneNumber;

    @NotBlank(message = "Username cannot be blank!")
    String username;

}
