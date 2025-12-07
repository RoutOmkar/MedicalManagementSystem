package com.hospital.repository;

import com.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    // Find patient by ID (already provided by JpaRepository as findById)
    Optional<Patient> findByPId(Integer pId);

    // Find patient by email
    Optional<Patient> findByEmail(String email);

    // Search patients by name (partial match, case-insensitive)
    List<Patient> findByNameContainingIgnoreCase(String name);

    // Search patients by contact number (corrected field name)
    List<Patient> findByContactContaining(String contact);

    // Find patients by blood group
    List<Patient> findByBloodGroup(String bloodGroup);

    // existsById is already provided by JpaRepository

    // Find patients associated with a particular doctor
    List<Patient> findDistinctByAppointmentsDoctorDrId(Integer doctorId);

}
