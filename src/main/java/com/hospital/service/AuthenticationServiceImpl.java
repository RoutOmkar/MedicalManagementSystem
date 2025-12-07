package com.hospital.service;

import com.hospital.dto.LoginRequest;
import com.hospital.dto.LoginResponse;
import com.hospital.entity.Admin;
import com.hospital.entity.Patient;
import com.hospital.entity.Doctor;
import com.hospital.repository.AdminRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // In-memory blacklist for tokens (basic logout mechanism)
    private final Set<String> blacklistedTokens = new HashSet<>();

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(admin.getEmail(), loginRequest.getUserType());

        return new LoginResponse(
                token,
                loginRequest.getUserType(),
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                "Login successful",
                true
        );
    }

    @Override
    public LoginResponse authenticatePatient(LoginRequest loginRequest) {
        Patient patient = patientRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), patient.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(patient.getEmail(), "PATIENT");

        return new LoginResponse(
                token,
                "PATIENT",
                patient.getpId(),
                patient.getName(),
                patient.getEmail(),
                "Login successful",
                true
        );
    }

    @Override
    public LoginResponse authenticateDoctor(LoginRequest loginRequest) {
        Doctor doctor = doctorRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), doctor.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(doctor.getEmail(), "DOCTOR");

        return new LoginResponse(
                token,
                "DOCTOR",
                doctor.getDrId(),
                doctor.getName(),
                doctor.getEmail(),
                "Login successful",
                true
        );
    }

    @Override
    public LoginResponse registerPatient(LoginRequest registerRequest) {
        // Check if patient already exists
        if (patientRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Patient with this email already exists");
        }

        registerRequest.setUserType("PATIENT");

        // Create new patient
        Patient patient = new Patient();
        patient.setEmail(registerRequest.getEmail());
        patient.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        patient.setName(registerRequest.getEmail().split("@")[0]); // Use email prefix as name
        
        Patient savedPatient = patientRepository.save(patient);

        String token = jwtUtil.generateToken(savedPatient.getEmail(), "PATIENT");

        return new LoginResponse(
                token,
                "PATIENT",
                savedPatient.getpId(),
                savedPatient.getName(),
                savedPatient.getEmail(),
                "Registration successful",
                true
        );
    }

    @Override
    public LoginResponse registerDoctor(LoginRequest registerRequest) {
        // Check if doctor already exists
        if (doctorRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Doctor with this email already exists");
        }

        // Create new doctor
        Doctor doctor = new Doctor();
        doctor.setEmail(registerRequest.getEmail());
        doctor.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        doctor.setName(registerRequest.getEmail().split("@")[0]); // Use email prefix as name
        
        Doctor savedDoctor = doctorRepository.save(doctor);

        String token = jwtUtil.generateToken(savedDoctor.getEmail(), "DOCTOR");

        return new LoginResponse(
                token,
                "DOCTOR",
                savedDoctor.getDrId(),
                savedDoctor.getName(),
                savedDoctor.getEmail(),
                "Registration successful",
                true
        );
    }

    @Override
    public void logout(String token) {
        blacklistedTokens.add(token);
    }

    @Override
    public boolean validateToken(String token) {
        if (blacklistedTokens.contains(token)) {
            return false;
        }

        try {
            String email = jwtUtil.extractEmail(token);
            return jwtUtil.validateToken(token, email);
        } catch (Exception e) {
            return false;
        }
    }
}
