package com.sda.clinicapi.repository;

import com.sda.clinicapi.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctor, Long> {

}
