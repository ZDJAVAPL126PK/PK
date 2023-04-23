package com.sda.clinicapi.mapper;

import com.sda.clinicapi.TestUtils;
import com.sda.clinicapi.dto.UserDTO;
import com.sda.clinicapi.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void testMapUserToUserDTONullParam() {
        // given
        User user = null;

        // when
        UserDTO userDTO = userMapper.map(user);

        // then
        Assertions.assertNull(userDTO.getEmail());
        Assertions.assertNull(userDTO.getUsername());
        Assertions.assertNull(userDTO.getPassword());
        Assertions.assertNull(userDTO.getRole());
        Assertions.assertFalse(userDTO.isEnabled());
    }

    @Test
    void testMapUserToUserDTO() {
        // given
        User user = TestUtils.createUser();

        // when
        UserDTO userDTO = userMapper.map(user);

        // then
        Assertions.assertEquals(user.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(user.getUsername(), userDTO.getUsername());
        Assertions.assertEquals(user.getPassword(), userDTO.getPassword());
        Assertions.assertEquals(user.getRole(), userDTO.getRole());
        Assertions.assertEquals(user.isEnabled(), userDTO.isEnabled());
    }


    @Test
    void testMapUserDTOToUserNullParam() {
        // given
        UserDTO userDTO = null;

        // when
        User user = userMapper.map(userDTO);

        // then
        Assertions.assertNull(user.getEmail());
        Assertions.assertNull(user.getUsername());
        Assertions.assertNull(user.getPassword());
        Assertions.assertNull(user.getRole());
        Assertions.assertFalse(user.isEnabled());
    }

    @Test
    void testMapUserDTOToUser() {
        // given
        UserDTO userDTO = TestUtils.createUserDTO();

        // when
        User user = userMapper.map(userDTO);

        // then
        Assertions.assertEquals(userDTO.getEmail(), user.getEmail());
        Assertions.assertEquals(userDTO.getUsername(), user.getUsername());
        Assertions.assertEquals(userDTO.getPassword(), user.getPassword());
        Assertions.assertEquals(userDTO.getRole(), user.getRole());
        Assertions.assertEquals(userDTO.isEnabled(), user.isEnabled());
    }
}