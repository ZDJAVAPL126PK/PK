package com.sda.clinicapi.model;

import com.sda.clinicapi.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role;

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.enabled = user.enabled;
        this.role = user.role;
    }
}
