package com.hospital.service;

import com.hospital.entity.Appointment;
import com.hospital.entity.Patient;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    Appointment createAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(Integer id);

    Appointment updateAppointment(Appointment appointment);

    void deleteAppointment(Integer id);

    List<Appointment> getAppointmentsByDoctor(Integer doctorId);

    List<Appointment> getAppointmentsByPatient(Integer patientId);

    List<Appointment> getAppointmentsByStatus(String status);

    List<Appointment> getAppointmentsByDate(String date);

    Appointment updateStatus(Integer id, String status);

    List<Appointment> getUpcomingAppointments(Integer doctorId);

    List<Appointment> getPastAppointments(Integer doctorId);

    List<Appointment> getTodayAppointments(Integer doctorId);

    boolean checkAvailability(Integer doctorId, String date, String time);

    List<Appointment> getAppointmentsByDoctorAndDate(Integer doctorId, String date);

    List<Patient> getPatientsByDoctorId(Integer doctorId);
}
