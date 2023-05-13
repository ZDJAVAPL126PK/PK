package com.sda.clinicapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.clinicapi.TestUtils;
import com.sda.clinicapi.config.SecurityConfig;
import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.exception.ErrorHandler;
import com.sda.clinicapi.exception.ErrorResponse;
import com.sda.clinicapi.exception.ResourceNotFoundException;
import com.sda.clinicapi.exception.ConflictException;
import com.sda.clinicapi.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UsersController.class)
@Import({SecurityConfig.class, ErrorHandler.class})
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersService usersService;

    @BeforeEach
    void reset() {
        Mockito.reset(usersService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testFindUserByUsernameNotFound() throws Exception {
        // given
        String username = "admin";
        String endpointPath = UsersController.API_USERS_PATH + "/{username}"; // "/api/users/{username}"

        ResourceNotFoundException exception = new ResourceNotFoundException("Not Found!");
        ErrorResponse errorResponse = ErrorResponse.of(exception, HttpStatus.NOT_FOUND);

        Mockito.when(usersService.findUserByUsername(username)).thenThrow(exception);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                // "/api/users/{username}", "admin"
                .get(endpointPath, username)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        // when / then
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(errorResponse.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(errorResponse.getError()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        Mockito.verify(usersService).findUserByUsername(username);
        Mockito.verifyNoMoreInteractions(usersService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testFindUserByUsernameSuccess() throws Exception {
        // given
        String username = "admin";
        String endpointPath = UsersController.API_USERS_PATH + "/{username}"; // "/api/users/admin"

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(endpointPath, username)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        UserDTO userDTO = TestUtils.createUserDTO(username);

        String jsonUser = objectMapper.writeValueAsString(userDTO);
        Mockito.when(usersService.findUserByUsername(username)).thenReturn(userDTO);

        // when / then
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonUser));

        Mockito.verify(usersService).findUserByUsername(username);
        Mockito.verifyNoMoreInteractions(usersService);
    }

    @Test
    @WithMockUser(roles = {"PATIENT", "DOCTOR"})
    void testFindUserByUsernameForbidden() throws Exception {
        // given
        String username = "admin";
        String endpointPath = UsersController.API_USERS_PATH + "/{username}"; // "/api/users/admin"

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(endpointPath, username);

        // when / then
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));

        Mockito.verifyNoInteractions(usersService);
    }

    @Test
    @WithMockUser(roles = {"PATIENT", "DOCTOR"})
    void testCreateForbidden() throws Exception {
        // given
        UserDTO userDTO = TestUtils.createUserDTO();
        String userJson = objectMapper.writeValueAsString(userDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(UsersController.API_USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson);

        // when / then
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));

        Mockito.verifyNoInteractions(usersService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUserWithUsernameAlreadyExists() throws Exception {
        // given
        String existingUsername = "existingUsername";
        UserDTO userDTO = TestUtils.createUserDTO(existingUsername);
        String userJson = objectMapper.writeValueAsString(userDTO);

        ConflictException exception = new ConflictException("UsernameConflictException");
        ErrorResponse errorResponse = ErrorResponse.of(exception, HttpStatus.CONFLICT);

        Mockito.doThrow(exception).when(usersService).create(userDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(UsersController.API_USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson);


        // when / then
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(errorResponse.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(errorResponse.getError()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        Mockito.verify(usersService).create(userDTO);
        Mockito.verifyNoMoreInteractions(usersService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUserSuccess() throws Exception {
        // given
        UserDTO userDTO = TestUtils.createUserDTO();
        String userJson = objectMapper.writeValueAsString(userDTO);

        Mockito.doNothing().when(usersService).create(userDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(UsersController.API_USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson);

        // when / then
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));

        Mockito.verify(usersService).create(userDTO);
        Mockito.verifyNoMoreInteractions(usersService);
    }
}