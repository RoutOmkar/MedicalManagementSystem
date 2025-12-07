package com.hospital.repository;

import com.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    // Find doctors by specialization ID (Specialization.id)
    List<Doctor> findBySpecializationId(Integer specializationId);

    // Search doctors by name (partial match, ignoring case)
    List<Doctor> findByNameContainingIgnoreCase(String name);

    // Find doctor by email
    Optional<Doctor> findByEmail(String email);
}
