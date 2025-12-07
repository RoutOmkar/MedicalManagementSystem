package com.hospital.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.entity.Admin;
import com.hospital.service.AdminService;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private AdminService adminService;

	// Get all admins
	@GetMapping
	public List<Admin> getAllAdmins() {
		return adminService.getAllAdmins();
	}

	// Get admin by id
	@GetMapping("/{id}")
	public Admin getAdminById(@PathVariable int id) {
		return adminService.getAdminById(id).orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
	}

	// Create new admin
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Admin createAdmin(@RequestBody Admin admin) {
		return adminService.saveAdmin(admin);
	}

	// Update admin
	@PutMapping("/{id}")
	public Admin updateAdmin(@PathVariable int id, @RequestBody Admin admin) {
		admin.setId(id);
		return adminService.updateAdmin(admin);
	}

	// Delete admin
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAdmin(@PathVariable int id) {
		adminService.deleteAdmin(id);
	}

	// Find by email
	@GetMapping("/email")
	public Admin findAdminByEmail(@RequestParam String email) {
		return adminService.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Admin not found with email: " + email));
	}

	// Change password
	@PutMapping("/{id}/change-password")
	public Admin changePassword(@PathVariable int id, @RequestBody Map<String, String> passwordData) {
		String currentPassword = passwordData.get("currentPassword");
		String newPassword = passwordData.get("newPassword");
		return adminService.changePassword(id, currentPassword, newPassword);
	}

}
