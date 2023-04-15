package com.sda.clinicapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/api/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello world!");
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR')")
    public ResponseEntity<String> hello2() {
        return ResponseEntity.ok("Hello2");
    }
}
