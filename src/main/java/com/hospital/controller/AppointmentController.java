package com.hospital.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.hospital.entity.Appointment;
import com.hospital.entity.Patient;
import com.hospital.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	// DTO for updating status
	public static class UpdateStatusRequest {
		private String status;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Appointment createAppointment(@RequestBody Appointment appointment) {
		return appointmentService.createAppointment(appointment);
	}

	@GetMapping
	public List<Appointment> getAllAppointments() {
		return appointmentService.getAllAppointments();
	}

	@GetMapping("/{id}")
	public Appointment getAppointmentById(@PathVariable Integer id) {
		return appointmentService.getAppointmentById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
	}

	@PutMapping("/{id}")
	public Appointment updateAppointment(@PathVariable Integer id, @RequestBody Appointment appointment) {
		appointment.setApId(id);
		return appointmentService.updateAppointment(appointment);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAppointment(@PathVariable Integer id) {
		appointmentService.deleteAppointment(id);
	}

	@GetMapping("/doctor/{doctorId}")
	public List<Appointment> getAppointmentsByDoctor(@PathVariable Integer doctorId) {
		return appointmentService.getAppointmentsByDoctor(doctorId);
	}

	@GetMapping("/patient/{patientId}")
	public List<Appointment> getAppointmentsByPatient(@PathVariable Integer patientId) {
		return appointmentService.getAppointmentsByPatient(patientId);
	}

	@GetMapping("/status/{status}")
	public List<Appointment> getAppointmentsByStatus(@PathVariable String status) {
		return appointmentService.getAppointmentsByStatus(status);
	}

	@GetMapping("/date/{date}")
	public List<Appointment> getAppointmentsByDate(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return appointmentService.getAppointmentsByDate(date.toString());
	}

	@PutMapping("/{id}/status")
	public Appointment updateAppointmentStatus(@PathVariable Integer id, @RequestBody UpdateStatusRequest request) {
		return appointmentService.updateStatus(id, request.getStatus());
	}

	@GetMapping("/doctor/{doctorId}/upcoming")
	public List<Appointment> getUpcomingAppointments(@PathVariable Integer doctorId) {
		return appointmentService.getUpcomingAppointments(doctorId);
	}

	@GetMapping("/doctor/{doctorId}/past")
	public List<Appointment> getPastAppointments(@PathVariable Integer doctorId) {
		return appointmentService.getPastAppointments(doctorId);
	}

	@GetMapping("/doctor/{doctorId}/today")
	public List<Appointment> getTodayAppointments(@PathVariable Integer doctorId) {
		return appointmentService.getTodayAppointments(doctorId);
	}

	@GetMapping("/check")
	public ResponseEntity<?> checkAvailability(@RequestParam Integer doctorId, @RequestParam String date,
			@RequestParam String time) {
		try {
			boolean isAvailable = appointmentService.checkAvailability(doctorId, date, time);
			return ResponseEntity.ok(Map.of("available", isAvailable));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/doctor/{doctorId}/date/{date}")
	public List<Appointment> getAppointmentsByDoctorAndDate(@PathVariable Integer doctorId,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return appointmentService.getAppointmentsByDoctorAndDate(doctorId, date.toString());
	}

	@GetMapping("/{doctorId}/patients")
	public List<Patient> getDoctorPatients(@PathVariable Integer doctorId) {
		return appointmentService.getPatientsByDoctorId(doctorId);
	}

	// Custom Exception for 404
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public static class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}
}
