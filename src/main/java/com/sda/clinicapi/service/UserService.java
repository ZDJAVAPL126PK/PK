package com.sda.clinicapi.service;

import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.exception.UsernameConflictException;
import com.sda.clinicapi.mapper.UserMapper;
import com.sda.clinicapi.model.User;
import com.sda.clinicapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void create(UserDTO userDTO) {
        String username = userDTO.getUsername();
        boolean exists = userRepository.existsById(username);
        if (exists) {
            throw new UsernameConflictException("User with given username already exists!");
        }

        User user = userMapper.map(userDTO);
        user.setEnabled(true);

        userRepository.save(user);
    }

}
