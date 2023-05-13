package com.sda.clinicapi.service;

import com.sda.clinicapi.exception.ConflictException;
import com.sda.clinicapi.exception.ResourceNotFoundException;
import com.sda.clinicapi.model.ConfirmationCode;
import com.sda.clinicapi.repository.ConfirmationCodesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmationCodesService {

    private final ConfirmationCodesRepository confirmationCodesRepository;

    public void deleteByCode(String code) {
        confirmationCodesRepository.deleteById(code);
    }

    public void validate(String code) {
        log.info("Validating code: '{}'.", code);
        ConfirmationCode confirmationCode = confirmationCodesRepository.findById(code)
                .orElseThrow(() -> {
                    String message = "Confirmation code does not exists!";
                    log.error(message);
                    throw new ResourceNotFoundException(message);
                });

        LocalDateTime expirationTimestamp = confirmationCode.getExpirationTimestamp();

        LocalDateTime currentTimestamp = LocalDateTime.now();

        if (currentTimestamp.isAfter(expirationTimestamp)) {
            String message = "Confirmation code expired!";
            log.error(message);
            throw new ConflictException(message);
        }

        log.info("Validation completed.");
    }

    public String generateCode() {
        log.info("Generating confirmation code...");

        String code = UUID.randomUUID().toString();
        LocalDateTime expirationTimestamp = LocalDateTime.now()
                .plus(Duration.of(1, ChronoUnit.DAYS));

        ConfirmationCode confirmationCode = ConfirmationCode.builder()
                .code(code)
                .expirationTimestamp(expirationTimestamp)
                .build();
        confirmationCodesRepository.save(confirmationCode);
        log.info("Confirmation code generation completed.");

        return code;
    }
}
