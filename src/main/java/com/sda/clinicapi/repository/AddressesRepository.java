package com.sda.clinicapi.repository;

import com.sda.clinicapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressesRepository extends JpaRepository<Address, Long> {

}
