package com.sda.clinicapi.controller;

import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "basicAuth")
public class UserController {

    private final UserService userService;

    // HTTP Methods
    // =============================
    // GET -> pobieramy zasoby  200 OK
    // POST -> tworzymy zasoby  201 CREATED
    // PUT -> całościowy update zasobów 201 CREATED
    // PATCH (Łatka) -> częściowy update zasobu 201 CREATED
    // DELETE -> usuwanie zasobów 204 NO_CONTENT
    // HEAD -> pobranie nagłówków
    // OPTIONS -> pobiera opcje, które mogą być wykonane na konkretnym zasobie

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDTO userDTO) {
        userService.create(userDTO);
    }

    // https://example.com?username=david  - Request Param
    @DeleteMapping("{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity.noContent().build();
    }
}
