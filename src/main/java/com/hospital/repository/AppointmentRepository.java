package com.hospital.repository;

import com.hospital.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    // Doctor-based queries (Doctor.drId)
    List<Appointment> findByDoctorDrId(Integer doctorId);

    // Patient-based queries (Patient.pId)
    List<Appointment> findByPatientPId(Integer patientId);

    // Common filters
    List<Appointment> findByStatus(String status);
    List<Appointment> findByAppointmentDate(LocalDate date);

    // Custom queries
    @Query("SELECT a FROM Appointment a WHERE a.doctor.drId = :doctorId AND a.appointmentDate = CURRENT_DATE")
    List<Appointment> findTodayAppointments(@Param("doctorId") Integer doctorId);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.drId = :doctorId AND a.appointmentDate > CURRENT_DATE")
    List<Appointment> findUpcomingAppointments(@Param("doctorId") Integer doctorId);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.drId = :doctorId AND a.appointmentDate < CURRENT_DATE")
    List<Appointment> findPastAppointments(@Param("doctorId") Integer doctorId);

    // Additional queries for availability checking
    List<Appointment> findByDoctorDrIdAndAppointmentDate(Integer doctorId, LocalDate date);
    
    List<Appointment> findByDoctorDrIdAndAppointmentDateAndAppointmentTime(
        Integer doctorId, LocalDate date, LocalTime time);

    @Query("SELECT COUNT(DISTINCT a.patient.pId) FROM Appointment a WHERE a.doctor.drId = :doctorId")
    long countDistinctPatientsByDoctorId(@Param("doctorId") Integer doctorId);
}
