package com.hospital.service;

import com.hospital.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    Doctor saveDoctor(Doctor doctor);

    List<Doctor> getAllDoctors();

    Optional<Doctor> getDoctorById(Integer id);

    Doctor updateDoctor(Doctor doctor);

    void deleteDoctor(Integer id);

    List<Doctor> getDoctorsBySpecializationId(Integer spId);

    List<Doctor> searchDoctorsByName(String name);

    Optional<Doctor> findByEmail(String email);

    boolean existsById(Integer id);

    long getPatientCount(Integer doctorId);
}
