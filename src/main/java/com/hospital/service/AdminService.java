package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.entity.Admin;
import com.hospital.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Create or save admin
	public Admin saveAdmin(Admin admin) {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		// doctor.setPassword(passwprdEncoder.encode(doctor.getPassword()));
		return adminRepository.save(admin);
	}

	// Get all admins
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	// Get admin by ID
	public Optional<Admin> getAdminById(Integer id) {
		return adminRepository.findById(id);
	}

	// Update admin
	public Admin updateAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

	// Delete admin by ID
	public void deleteAdmin(Integer id) {
		adminRepository.deleteById(id);
	}

	// Find admin by email
	public Optional<Admin> findByEmail(String email) {
		return adminRepository.findByEmail(email);
	}

	// Change admin password
	public Admin changePassword(Integer id, String currentPassword, String newPassword) {
		Optional<Admin> optionalAdmin = adminRepository.findById(id);
		if (optionalAdmin.isPresent()) {
			Admin admin = optionalAdmin.get();
			if (admin.getPassword().equals(currentPassword)) {
				admin.setPassword(newPassword);
				return adminRepository.save(admin);
			} else {
				throw new RuntimeException("Current password is incorrect");
			}
		} else {
			throw new RuntimeException("Admin not found");
		}
	}
}
