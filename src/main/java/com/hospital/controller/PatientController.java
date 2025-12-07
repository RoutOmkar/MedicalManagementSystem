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

import com.hospital.entity.Patient;
import com.hospital.service.PatientService;
import com.hospital.service.AnalyticsService;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable int id) {
        return patientService.getPatientById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable int id, @RequestBody Patient patient) {
        return patientService.updatePatient(patient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
    }

    @GetMapping("/bloodgroup/{bloodGroup}")
    public List<Patient> getPatientsByBloodGroup(@PathVariable String bloodGroup) {
        return patientService.getPatientsByBloodGroup(bloodGroup);
    }

    @GetMapping("/search")
    public List<Patient> searchPatientsByName(@RequestParam String name) {
        return patientService.searchPatientsByName(name);
    }

    @GetMapping("/email")
    public Patient findPatientByEmail(@RequestParam String email) {
        return patientService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient not found with email: " + email));
    }

    @GetMapping("/search-contact")
    public List<Patient> searchPatientsByContact(@RequestParam String contact) {
        return patientService.findByContactContaining(contact);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Patient> getPatientsByDoctor(@PathVariable int doctorId) {
        return patientService.findByDoctorId(doctorId);
    }

    @GetMapping("/{id}/stats")
    public Map<String, Object> getPatientStats(@PathVariable Integer id) {
        return analyticsService.getPatientStats(id);
    }

    @PutMapping("/{id}/change-password")
    public Patient changePassword(@PathVariable int id, @RequestBody Map<String, String> passwordData) {
        String currentPassword = passwordData.get("currentPassword");
        String newPassword = passwordData.get("newPassword");
        return patientService.changePassword(id, currentPassword, newPassword);
    }
}