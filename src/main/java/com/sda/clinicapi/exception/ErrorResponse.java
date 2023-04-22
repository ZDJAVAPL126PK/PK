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

    static ErrorResponse of(Exception ex, HttpStatus status) {
        return of(ex.getMessage(), status);
    }

    static ErrorResponse of(String message, HttpStatus status) {
        return ErrorResponse.builder()
                .code(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
