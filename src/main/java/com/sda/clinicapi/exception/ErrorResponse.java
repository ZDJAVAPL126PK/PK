package com.sda.clinicapi.exception;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Value
@Builder
public class ErrorResponse {

    int code;
    String error;
    String message;
    LocalDateTime timestamp;

    public static ErrorResponse of(Exception ex, HttpStatus status) {
        return of(ex.getMessage(), status);
    }

    public static ErrorResponse of(String message, HttpStatus status) {
        return ErrorResponse.builder()
                .code(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
