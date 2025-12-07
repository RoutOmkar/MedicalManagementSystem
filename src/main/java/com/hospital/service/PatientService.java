package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.Patient;


@Service
public interface PatientService {

    Patient save(Patient patient);

    List<Patient> getAllPatients();

    Optional<Patient> getPatientById(Integer id);

    Patient updatePatient(Patient patient); // ID is part of the Patient object

    void deletePatient(Integer id);

    Optional<Patient> findByEmail(String email);

    List<Patient> getPatientsByBloodGroup(String bloodGroup);

    boolean existsById(Integer id);

    List<Patient> findByNameContaining(String name);

    List<Patient> findByContactContaining(String contact);

    List<Patient> findByDoctorId(Integer doctorId);

    Patient changePassword(Integer id, String currentPassword, String newPassword);

	List<Patient> searchPatientsByName(String name);
}
