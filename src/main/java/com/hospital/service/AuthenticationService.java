package com.hospital.service;

import com.hospital.dto.LoginRequest;
import com.hospital.dto.LoginResponse;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest loginRequest);
    LoginResponse authenticatePatient(LoginRequest loginRequest);
    LoginResponse authenticateDoctor(LoginRequest loginRequest);
    LoginResponse registerPatient(LoginRequest registerRequest);
    LoginResponse registerDoctor(LoginRequest registerRequest);
    void logout(String token);
    boolean validateToken(String token);
}
