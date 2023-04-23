package com.sda.clinicapi.service;

import com.sda.clinicapi.TestUtils;
import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.exception.ResourceNotFoundException;
import com.sda.clinicapi.exception.UsernameConflictException;
import com.sda.clinicapi.mapper.UserMapper;
import com.sda.clinicapi.model.User;
import com.sda.clinicapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@SpringBootTest
class UserServiceTest {

//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private UserService userService;
//
//    @MockBean
//    private UserRepository userRepository;

    private final UserMapper userMapper = new UserMapper();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserService(userMapper, userRepository, passwordEncoder);


    @BeforeEach
    void reset() {
        Mockito.reset(userRepository);
    }

    @Test
    void testFindAllEmpty() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<User> page = new PageImpl<>(Collections.emptyList());

        Mockito.when(userRepository.findAll(pageRequest)).thenReturn(page);

        // when
        List<UserDTO> actualList = userService.findAll(pageRequest);

        // then
        Assertions.assertTrue(actualList.isEmpty());
        Mockito.verify(userRepository).findAll(pageRequest);
        Mockito.verifyNoMoreInteractions(userRepository);
    }


    @Test
    void testFindAllNotEmpty() {
        // given
        int expectedSize = 2;
        User user1 = TestUtils.createUser();
        User user2 = TestUtils.createUser();

        UserDTO userDTO1 = userMapper.map(user1);
        UserDTO userDTO2 = userMapper.map(user2);

        List<User> pageContent = List.of(user1, user2);

        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<User> page = new PageImpl<>(pageContent);

        Mockito.when(userRepository.findAll(pageRequest)).thenReturn(page);

        // when
        List<UserDTO> actualList = userService.findAll(pageRequest);

        // then
        Assertions.assertEquals(expectedSize, actualList.size());
        Assertions.assertTrue(actualList.containsAll(List.of(userDTO1, userDTO2)));

        Mockito.verify(userRepository).findAll(pageRequest);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testFindUserByUsernameNotFound() {
        // given
        String nonExistingUsername = "nonExistingUsername";

        Mockito.when(userRepository.findByUsername(nonExistingUsername))
                .thenReturn(Optional.empty());

        // when
        Executable executable = () -> userService.findUserByUsername(nonExistingUsername);

        // then
        Assertions.assertThrows(ResourceNotFoundException.class, executable);
        Mockito.verify(userRepository).findByUsername(nonExistingUsername);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testFindUserByUsernameUserExists() {
        // given
        String username = "admin";
        User user = TestUtils.createUser(username);
        UserDTO expectedUserDTO = userMapper.map(user);

        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        // when
        UserDTO actualUserDTO = userService.findUserByUsername(username);

        // then
        Assertions.assertEquals(expectedUserDTO, actualUserDTO);
        Mockito.verify(userRepository).findByUsername(username);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testDeleteUserNotFound() {
        // given
        String nonExistingUsername = "nonExistingUsername";
        Mockito.when(userRepository.existsById(nonExistingUsername)).thenReturn(false);

        // when
        Executable executable = () -> userService.delete(nonExistingUsername);

        // then
        Assertions.assertThrows(ResourceNotFoundException.class, executable);
        Mockito.verify(userRepository).existsById(nonExistingUsername);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testDeleteSuccess() {
        // given
        String username = "admin";
        Mockito.when(userRepository.existsById(username)).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(username);

        // when
        userService.delete(username);

        // then
        Mockito.verify(userRepository).existsById(username);
        Mockito.verify(userRepository).deleteById(username);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testCreateUsernameConflict() {
        // given
        String existingUsername = "existingUsername";
        UserDTO userDTO = TestUtils.createUserDTO(existingUsername);

        Mockito.when(userRepository.existsById(existingUsername)).thenReturn(true);

        // when
        Executable executable = () -> userService.create(userDTO);

        // then
        Assertions.assertThrows(UsernameConflictException.class, executable);
        Mockito.verify(userRepository).existsById(existingUsername);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testCreateSuccess() {
        // given
        String username = "username";
        UserDTO userDTO = TestUtils.createUserDTO(username);

        Mockito.when(userRepository.existsById(username)).thenReturn(false);

        // when
        userService.create(userDTO);

        // then
        Mockito.verify(userRepository).existsById(username);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testUpdateUserHappyPath() {
        // given
        String username = "username";
        UserDTO userDTO = TestUtils.createUserDTO(username);

        Mockito.when(userRepository.existsById(username)).thenReturn(true);

        // when
        userService.update(username, userDTO);

        // then
        Mockito.verify(userRepository).existsById(username);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testUpdateUsernameConflict() {
        // given
        String usernameParam = "username";
        UserDTO userDTO = TestUtils.createUserDTO("diff username");

        // when
        Executable executable = () -> userService.update(usernameParam, userDTO);

        // then
        Assertions.assertThrows(UsernameConflictException.class, executable);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    void testUpdateUserNotFound() {
        // given
        String nonExistingUsername = "nonExistingUsername";
        UserDTO userDTO = TestUtils.createUserDTO(nonExistingUsername);

        Mockito.when(userRepository.existsById(nonExistingUsername)).thenReturn(false);

        // when
        Executable executable = () -> userService.update(nonExistingUsername, userDTO);

        // then
        Assertions.assertThrows(ResourceNotFoundException.class, executable);
        Mockito.verify(userRepository).existsById(nonExistingUsername);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

}