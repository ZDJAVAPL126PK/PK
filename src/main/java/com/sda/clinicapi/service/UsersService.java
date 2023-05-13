package com.sda.clinicapi.service;

import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.exception.ConflictException;
import com.sda.clinicapi.exception.ResourceNotFoundException;
import com.sda.clinicapi.mapper.UserMapper;
import com.sda.clinicapi.model.User;
import com.sda.clinicapi.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserMapper userMapper;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDTO> findAll(PageRequest pageRequest) {
        log.info("Fetching all users...");
        List<UserDTO> users = usersRepository.findAll(pageRequest).stream()
                .map(userMapper::map)
                .toList();

        log.info("Fetch completed.");
        return users;
    }

    public UserDTO findUserByUsername(String username) {
        log.info("Fetching user with username '{}'.", username);
        UserDTO userDTO = usersRepository.findByUsername(username)
                .map(user -> userMapper.map(user))
                .orElseThrow(() -> {
                    String message = "User with given username does not exists!";
                    log.error(message);
                    throw new ResourceNotFoundException(message);
                });
        log.info("Fetch completed.");
        return userDTO;
    }

    public void create(UserDTO userDTO) {
        String username = userDTO.getUsername();
        log.info("Creating user with username: '{}'.", username);

        boolean exists = usersRepository.existsById(username);
        if (exists) {
            String message = "User with given username already exists!";
            log.error(message);
            throw new ConflictException(message);
        }

        User user = userMapper.map(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        usersRepository.save(user);
        log.info("Creating user completed.");
    }

    public void delete(String username) {
        log.info("Removing user with username '{}'.", username);
        checkIfUserExists(username);
        usersRepository.deleteById(username);
        log.info("Removing user completed.");
    }

    public void update(String username, UserDTO userDTO) {
        log.info("Updating user with username '{}'.", username);

        if (!username.equals(userDTO.getUsername())) {
            String message = "Usernames does not match!";
            log.error(message);
            throw new ConflictException(message);
        }

        checkIfUserExists(username);
        User user = userMapper.map(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        log.info("Updating user completed.");
    }

    public void enable(String username) {
        usersRepository.enable(username);
    }

    public void disable(String username) {
        usersRepository.disable(username);
    }

    private void checkIfUserExists(String username) {
        boolean exists = usersRepository.existsById(username);
        if (!exists) {
            String message = "User with given username does not exists!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

}
