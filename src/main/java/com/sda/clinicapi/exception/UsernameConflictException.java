package com.sda.clinicapi.exception;

public class UsernameConflictException extends RuntimeException {

    public UsernameConflictException(String message) {
        super(message);
    }
}
