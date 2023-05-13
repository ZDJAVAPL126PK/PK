package com.sda.clinicapi.repository;

import com.sda.clinicapi.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsRepository extends JpaRepository<Patient, Long> {

}
