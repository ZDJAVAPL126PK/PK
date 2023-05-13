package com.sda.clinicapi.service;

import com.sda.clinicapi.dto.DoctorDTO;
import com.sda.clinicapi.exception.ConflictException;
import com.sda.clinicapi.exception.ResourceNotFoundException;
import com.sda.clinicapi.mapper.DoctorsMapper;
import com.sda.clinicapi.model.Doctor;
import com.sda.clinicapi.repository.DoctorsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorsService {

    private final DoctorsMapper doctorsMapper;
    private final DoctorsRepository doctorsRepository;

    public List<DoctorDTO> findAll(PageRequest pageRequest) {
        log.info("Fetching all doctors...");
        List<DoctorDTO> doctors = doctorsRepository.findAll(pageRequest).stream()
                .map(doctorsMapper::map)
                .toList();

        log.info("Fetch completed.");
        return doctors;
    }

    public DoctorDTO findById(Long id) {
        log.info("Fetching doctor with id: '{}'.", id);
        DoctorDTO doctorDTO = doctorsRepository.findById(id)
                .map(doctor -> doctorsMapper.map(doctor))
                .orElseThrow(() -> {
                    String message = "Doctor with given id does not exists!";
                    log.error(message);
                    throw new ResourceNotFoundException(message);
                });
        log.info("Fetch completed.");
        return doctorDTO;
    }

    public void create(DoctorDTO doctorDTO) {
        log.info("Creating doctor: '{}'.", doctorDTO);
        Doctor doctor = doctorsMapper.map(doctorDTO);
        doctorsRepository.save(doctor);
        log.info("Creating doctor completed.");
    }


    public void update(Long id, DoctorDTO doctorDTO) {
        log.info("Updating doctor with id: '{}'.", id);

        if (!id.equals(doctorDTO.getId())) {
            String message = "Doctor ids does not match!";
            log.error(message);
            throw new ConflictException(message);
        }

        checkIfDoctorExists(id);
        Doctor doctor = doctorsMapper.map(doctorDTO);
        doctorsRepository.save(doctor);
        log.info("Updating doctor completed.");
    }

    private void checkIfDoctorExists(Long id) {
        boolean exists = doctorsRepository.existsById(id);
        if (!exists) {
            String message = "Doctor with given id does not exists!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

}
