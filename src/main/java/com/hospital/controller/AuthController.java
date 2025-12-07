package com.hospital.controller;

import com.hospital.dto.LoginRequest;
import com.hospital.dto.LoginResponse;
import com.hospital.entity.Admin;
import com.hospital.repository.AdminRepository;
import com.hospital.security.JwtUtil;
import com.hospital.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(
                new LoginResponse(
                    null,
                    loginRequest.getUserType(),
                    0,
                    null,
                    loginRequest.getEmail(),
                    e.getMessage(),
                    false
                )
            );
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword())); // 🔐 Secure hashing
        return ResponseEntity.ok(adminRepository.save(admin));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authenticationService.logout(token);
            return ResponseEntity.ok("Logged out successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid Authorization header.");
    }

    @PostMapping("/patient/login")
    public ResponseEntity<LoginResponse> patientLogin(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticatePatient(loginRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(
                new LoginResponse(
                    null,
                    "PATIENT",
                    0,
                    null,
                    loginRequest.getEmail(),
                    e.getMessage(),
                    false
                )
            );
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody LoginRequest loginRequest) {
        try {
            // Assuming the generic authenticate method can handle admins based on userType
            return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(
                new LoginResponse(
                    null,
                    "ADMIN",
                    0,
                    null,
                    loginRequest.getEmail(),
                    e.getMessage(),
                    false
                )
            );
        }
    }

    @PostMapping("/doctor/login")
    public ResponseEntity<LoginResponse> doctorLogin(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticateDoctor(loginRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(
                new LoginResponse(
                    null,
                    "DOCTOR",
                    0,
                    null,
                    loginRequest.getEmail(),
                    e.getMessage(),
                    false
                )
            );
        }
    }

    @PostMapping("/patient/register")
    public ResponseEntity<LoginResponse> registerPatient(@RequestBody LoginRequest registerRequest) {
        try {
            return ResponseEntity.ok(authenticationService.registerPatient(registerRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(
                new LoginResponse(
                    null,
                    "PATIENT",
                    0,
                    null,
                    registerRequest.getEmail(),
                    e.getMessage(),
                    false
                )
            );
        }
    }

    @PostMapping("/doctor/register")
    public ResponseEntity<LoginResponse> registerDoctor(@RequestBody LoginRequest registerRequest) {
        try {
            return ResponseEntity.ok(authenticationService.registerDoctor(registerRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(
                new LoginResponse(
                    null,
                    "DOCTOR",
                    0,
                    null,
                    registerRequest.getEmail(),
                    e.getMessage(),
                    false
                )
            );
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            boolean isValid = authenticationService.validateToken(token);
            if (isValid) {
                return ResponseEntity.ok("Token is valid");
            }
        }
        return ResponseEntity.status(401).body("Invalid token");
    }

}
