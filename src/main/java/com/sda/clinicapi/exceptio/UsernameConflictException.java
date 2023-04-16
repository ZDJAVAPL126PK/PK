package com.sda.clinicapi.exceptio;

public class UsernameConflictException extends RuntimeException {

    public UsernameConflictException(String message) {
        super(message);
    }
}
