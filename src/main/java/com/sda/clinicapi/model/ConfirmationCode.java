package com.sda.clinicapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "confirmation_codes")
public class ConfirmationCode {

    @Id
    @Column(nullable = false, length = 36)
    private String code;

    @Column(nullable = false, length = 50)
    private LocalDateTime expirationTimestamp;
}
