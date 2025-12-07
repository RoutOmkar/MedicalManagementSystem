package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.entity.Doctor;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public Doctor saveDoctor(Doctor doctor) {

		return doctorRepository.save(doctor);
	}

	@Override
	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

	@Override
	public Optional<Doctor> getDoctorById(Integer id) {
		return doctorRepository.findById(id);
	}

	@Override
	public Doctor updateDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}

	@Override
	public void deleteDoctor(Integer id) {
		doctorRepository.deleteById(id);
	}

	@Override
	public List<Doctor> getDoctorsBySpecializationId(Integer specializationId) {
		// ✅ Correct method name that matches DoctorRepository
		return doctorRepository.findBySpecializationId(specializationId);
	}

	@Override
	public List<Doctor> searchDoctorsByName(String name) {
		return doctorRepository.findByNameContainingIgnoreCase(name);
	}

	@Override
	public Optional<Doctor> findByEmail(String email) {
		return doctorRepository.findByEmail(email);
	}

	@Override
	public boolean existsById(Integer id) {
		return doctorRepository.existsById(id);
	}

	@Override
	public long getPatientCount(Integer doctorId) {
		return appointmentRepository.countDistinctPatientsByDoctorId(doctorId);
	}
}
