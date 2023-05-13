package com.sda.clinicapi.repository;

import com.sda.clinicapi.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointment, Long> {
}
