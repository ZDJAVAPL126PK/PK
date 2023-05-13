package com.sda.clinicapi.service;

import com.sda.clinicapi.TestUtils;
import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.exception.ResourceNotFoundException;
import com.sda.clinicapi.exception.ConflictException;
import com.sda.clinicapi.mapper.UserMapper;
import com.sda.clinicapi.model.User;
import com.sda.clinicapi.repository.UsersRepository;
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
class UsersServiceTest {

//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private UserService userService;
//
//    @MockBean
//    private UserRepository userRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
    private final UsersService usersService = new UsersService(userMapper, usersRepository, passwordEncoder);


    @BeforeEach
    void reset() {
        Mockito.reset(usersRepository);
    }

    @Test
    void testFindAllEmpty() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<User> page = new PageImpl<>(Collections.emptyList());

        Mockito.when(usersRepository.findAll(pageRequest)).thenReturn(page);

        // when
        List<UserDTO> actualList = usersService.findAll(pageRequest);

        // then
        Assertions.assertTrue(actualList.isEmpty());
        Mockito.verify(usersRepository).findAll(pageRequest);
        Mockito.verifyNoMoreInteractions(usersRepository);
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

        Mockito.when(usersRepository.findAll(pageRequest)).thenReturn(page);

        // when
        List<UserDTO> actualList = usersService.findAll(pageRequest);

        // then
        Assertions.assertEquals(expectedSize, actualList.size());
        Assertions.assertTrue(actualList.containsAll(List.of(userDTO1, userDTO2)));

        Mockito.verify(usersRepository).findAll(pageRequest);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void testFindUserByUsernameNotFound() {
        // given
        String nonExistingUsername = "nonExistingUsername";

        Mockito.when(usersRepository.findByUsername(nonExistingUsername))
                .thenReturn(Optional.empty());

        // when
        Executable executable = () -> usersService.findUserByUsername(nonExistingUsername);

        // then
        Assertions.assertThrows(ResourceNotFoundException.class, executable);
        Mockito.verify(usersRepository).findByUsername(nonExistingUsername);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void testFindUserByUsernameUserExists() {
        // given
        String username = "admin";
        User user = TestUtils.createUser(username);
        UserDTO expectedUserDTO = userMapper.map(user);

        Mockito.when(usersRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        // when
        UserDTO actualUserDTO = usersService.findUserByUsername(username);

        // then
        Assertions.assertEquals(expectedUserDTO, actualUserDTO);
        Mockito.verify(usersRepository).findByUsername(username);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void testDeleteUserNotFound() {
        // given
        String nonExistingUsername = "nonExistingUsername";
        Mockito.when(usersRepository.existsById(nonExistingUsername)).thenReturn(false);

        // when
        Executable executable = () -> usersService.delete(nonExistingUsername);

        // then
        Assertions.assertThrows(ResourceNotFoundException.class, executable);
        Mockito.verify(usersRepository).existsById(nonExistingUsername);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void testDeleteSuccess() {
        // given
        String username = "admin";
        Mockito.when(usersRepository.existsById(username)).thenReturn(true);
        Mockito.doNothing().when(usersRepository).deleteById(username);

        // when
        usersService.delete(username);

        // then
        Mockito.verify(usersRepository).existsById(username);
        Mockito.verify(usersRepository).deleteById(username);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void testCreateUsernameConflict() {
        // given
        String existingUsername = "existingUsername";
        UserDTO userDTO = TestUtils.createUserDTO(existingUsername);

        Mockito.when(usersRepository.existsById(existingUsername)).thenReturn(true);

        // when
        Executable executable = () -> usersService.create(userDTO);

        // then
        Assertions.assertThrows(ConflictException.class, executable);
        Mockito.verify(usersRepository).existsById(existingUsername);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void testCreateSuccess() {
        // given
        String username = "username";
        UserDTO userDTO = TestUtils.createUserDTO(username);

        Mockito.when(usersRepository.existsById(username)).thenReturn(false);

        // when
        usersService.create(userDTO);

        // then
        Mockito.verify(usersRepository).existsById(username);
        Mockito.verify(usersRepository).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void testUpdateUserHappyPath() {
        // given
        String username = "username";
        UserDTO userDTO = TestUtils.createUserDTO(username);

        Mockito.when(usersRepository.existsById(username)).thenReturn(true);

        // when
        usersService.update(username, userDTO);

        // then
        Mockito.verify(usersRepository).existsById(username);
        Mockito.verify(usersRepository).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void testUpdateUsernameConflict() {
        // given
        String usernameParam = "username";
        UserDTO userDTO = TestUtils.createUserDTO("diff username");

        // when
        Executable executable = () -> usersService.update(usernameParam, userDTO);

        // then
        Assertions.assertThrows(ConflictException.class, executable);
        Mockito.verifyNoInteractions(usersRepository);
    }

    @Test
    void testUpdateUserNotFound() {
        // given
        String nonExistingUsername = "nonExistingUsername";
        UserDTO userDTO = TestUtils.createUserDTO(nonExistingUsername);

        Mockito.when(usersRepository.existsById(nonExistingUsername)).thenReturn(false);

        // when
        Executable executable = () -> usersService.update(nonExistingUsername, userDTO);

        // then
        Assertions.assertThrows(ResourceNotFoundException.class, executable);
        Mockito.verify(usersRepository).existsById(nonExistingUsername);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

}