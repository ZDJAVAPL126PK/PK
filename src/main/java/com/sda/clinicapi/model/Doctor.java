package com.sda.clinicapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Setter
@Getter
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String phoneNumber;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private Set<Address> address;

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;
}
