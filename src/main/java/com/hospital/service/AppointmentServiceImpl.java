package com.hospital.service;

import com.hospital.entity.Appointment;
import com.hospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import com.hospital.entity.Patient;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Integer id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Integer id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(Integer doctorId) {
        return appointmentRepository.findByDoctorDrId(doctorId);
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(Integer patientId) {
        return appointmentRepository.findByPatientPId(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return appointmentRepository.findByAppointmentDate(localDate);
    }

    @Override
    public Appointment updateStatus(Integer id, String status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getUpcomingAppointments(Integer doctorId) {
        return appointmentRepository.findUpcomingAppointments(doctorId);
    }

    @Override
    public List<Appointment> getPastAppointments(Integer doctorId) {
        return appointmentRepository.findPastAppointments(doctorId);
    }

    @Override
    public List<Appointment> getTodayAppointments(Integer doctorId) {
        return appointmentRepository.findTodayAppointments(doctorId);
    }

    @Override
    public boolean checkAvailability(Integer doctorId, String date, String time) {
        LocalDate appointmentDate = LocalDate.parse(date);
        LocalTime appointmentTime = LocalTime.parse(time);
        
        List<Appointment> existingAppointments = appointmentRepository
            .findByDoctorDrIdAndAppointmentDateAndAppointmentTime(doctorId, appointmentDate, appointmentTime);
        
        return existingAppointments.isEmpty();
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorAndDate(Integer doctorId, String date) {
        LocalDate localDate = LocalDate.parse(date);
        return appointmentRepository.findByDoctorDrIdAndAppointmentDate(doctorId, localDate);
    }

    @Override
    public List<Patient> getPatientsByDoctorId(Integer doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorDrId(doctorId);
        return appointments.stream()
                         .map(Appointment::getPatient)
                         .distinct()
                         .collect(Collectors.toList());
    }
}
