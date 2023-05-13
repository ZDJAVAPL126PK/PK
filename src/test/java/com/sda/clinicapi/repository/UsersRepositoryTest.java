package com.sda.clinicapi.repository;

import com.sda.clinicapi.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Sql(scripts = "classpath:sql/users-repo-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void testFindAll() {
        // given
        int expectedSize = 3;

        // when
        List<User> users = usersRepository.findAll();

        // then
        Assertions.assertEquals(expectedSize, users.size());
    }

    @Test
    void testFindByUsernameNotFound() {
        // given
        String nonExistingUsername = "nonExistingUsername";

        // when
        Optional<User> optionalUser = usersRepository.findByUsername(nonExistingUsername);

        // then
        Assertions.assertFalse(optionalUser.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"admin", "doctor", "user"})
    void testFindByUsernameSuccess(String username) {
        // when
        Optional<User> optionalUser = usersRepository.findByUsername(username);

        // then
        Assertions.assertTrue(optionalUser.isPresent());
    }
}