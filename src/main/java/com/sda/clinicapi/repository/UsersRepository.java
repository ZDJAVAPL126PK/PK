package com.sda.clinicapi.repository;

import com.sda.clinicapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {

//    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.enabled = TRUE WHERE u.username = :username")
    void enable(String username);

    @Modifying
    @Query("UPDATE User u SET u.enabled = FALSE WHERE u.username = :username")
    void disable(String username);
}
