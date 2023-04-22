package com.sda.clinicapi.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Value
@Builder(access = AccessLevel.PACKAGE)
public class ErrorResponse {

    int code;
    String error;
    String message;
    LocalDateTime timestamp;

    static ErrorResponse of(RuntimeException ex, HttpStatus status) {
        return ErrorResponse.builder()
                .code(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
