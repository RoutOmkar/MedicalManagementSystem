package com.hospital.service;

import com.hospital.entity.Patient;
import com.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Patient save(Patient patient) {
        // Encode password before saving
        if (patient.getPassword() != null) {
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        }
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Integer id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Integer id) {
        patientRepository.deleteById(id);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    public List<Patient> getPatientsByBloodGroup(String bloodGroup) {
        return patientRepository.findByBloodGroup(bloodGroup);
    }

    @Override
    public boolean existsById(Integer id) {
        return patientRepository.existsById(id);
    }

    @Override
    public List<Patient> findByNameContaining(String name) {
        return patientRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Patient> findByContactContaining(String contact) {
        return patientRepository.findByContactContaining(contact);
    }

    @Override
    public List<Patient> findByDoctorId(Integer doctorId) {
        return patientRepository.findDistinctByAppointmentsDoctorDrId(doctorId);
    }

    @Override
    public Patient changePassword(Integer id, String currentPassword, String newPassword) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            if (patient.getPassword().equals(currentPassword)) {
                patient.setPassword(newPassword);
                return patientRepository.save(patient);
            } else {
                throw new RuntimeException("Current password is incorrect");
            }
        } else {
            throw new RuntimeException("Patient not found");
        }
    }

    @Override
    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByNameContainingIgnoreCase(name);
    }
}
