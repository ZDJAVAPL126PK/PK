package com.sda.clinicapi.controller;

import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "Users Controller")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "basicAuth")
@RequestMapping(UsersController.API_USERS_PATH)
public class UsersController {

    public static final String API_USERS_PATH = "/api/users";

    private final UsersService usersService;

    // HTTP Methods
    // =============================
    // GET -> pobieramy zasoby  200 OK
    // POST -> tworzymy zasoby  201 CREATED
    // PUT -> całościowy update zasobów 201 CREATED
    // PATCH (Łatka) -> częściowy update zasobu 201 CREATED
    // DELETE -> usuwanie zasobów 204 NO_CONTENT
    // HEAD -> pobranie nagłówków
    // OPTIONS -> pobiera opcje, które mogą być wykonane na konkretnym zasobie

    // https://example.com?pageNo=0&pageSize=10  - Request Param
    @GetMapping
    @Operation(summary = "Method is being used to get list of all users.")
    public ResponseEntity<List<UserDTO>> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "500") int pageSize,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "username") String sortColumn) {

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, direction, sortColumn);
        return ResponseEntity.ok(usersService.findAll(pageRequest));
    }

    @GetMapping("{username}")
    @Operation(summary = "Method is being used to get user with given username.")
    public ResponseEntity<UserDTO> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(usersService.findUserByUsername(username));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Method is being used to create new user.")
    public void create(@Valid @RequestBody UserDTO userDTO) {
        usersService.create(userDTO);
    }

    @DeleteMapping("{username}")
    @Operation(summary = "Method is being used to delete user with given username.")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        usersService.delete(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{username}")
    @Operation(summary = "Method is being used to update existing user.")
    public void update(@Valid @RequestBody UserDTO userDTO, @PathVariable String username) {
        usersService.update(username, userDTO);
    }
}
