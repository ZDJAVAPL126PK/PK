package com.sda.clinicapi.service;

import com.sda.clinicapi.dto.PatientDTO;
import com.sda.clinicapi.exception.ConflictException;
import com.sda.clinicapi.exception.ResourceNotFoundException;
import com.sda.clinicapi.mapper.PatientsMapper;
import com.sda.clinicapi.model.Patient;
import com.sda.clinicapi.repository.PatientsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientsService {

    private final PatientsMapper patientsMapper;
    private final PatientsRepository patientsRepository;

    public List<PatientDTO> findAll(PageRequest pageRequest) {
        log.info("Fetching all patients...");
        List<PatientDTO> patients = patientsRepository.findAll(pageRequest).stream()
                .map(patientsMapper::map)
                .toList();

        log.info("Fetch completed.");
        return patients;
    }

    public PatientDTO findById(Long id) {
        log.info("Fetching patient with id: '{}'.", id);
        PatientDTO patientDTO = patientsRepository.findById(id)
                .map(patientsMapper::map)
                .orElseThrow(() -> {
                    String message = "Patient with given id does not exists!";
                    log.error(message);
                    throw new ResourceNotFoundException(message);
                });
        log.info("Fetch completed.");
        return patientDTO;
    }

    public void create(PatientDTO patientDTO) {
        log.info("Creating patient: '{}'.", patientDTO);
        Patient patient = patientsMapper.map(patientDTO);
        patientsRepository.save(patient);
        log.info("Creating patient completed.");
    }

    public void update(Long id, PatientDTO patientDTO) {
        log.info("Updating patient with id: '{}'.", id);

        if (!id.equals(patientDTO.getId())) {
            String message = "Patient ids does not match!";
            log.error(message);
            throw new ConflictException(message);
        }

        checkIfPatientExists(id);
        Patient patient = patientsMapper.map(patientDTO);
        patientsRepository.save(patient);
        log.info("Updating patient completed.");
    }

    private void checkIfPatientExists(Long id) {
        boolean exists = patientsRepository.existsById(id);
        if (!exists) {
            String message = "Patient with given id does not exists!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }
}
