package com.hospital.repository;

import com.hospital.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    // Find prescriptions by patient
    List<Prescription> findByPatientPId(Integer patientId);

    // Find prescriptions by appointment
    List<Prescription> findByAppointmentApId(Integer appointmentId);

    // Find prescriptions by doctor (via appointment)
    @Query("SELECT p FROM Prescription p WHERE p.appointment.doctor.drId = :doctorId")
    List<Prescription> findByDoctorDrId(Integer doctorId);

    // Find prescriptions by appointment date
    @Query("SELECT p FROM Prescription p WHERE p.appointment.appointmentDate = :date")
    List<Prescription> findByDate(LocalDate date);  // <-- method name aligned with service

    // existsById is already provided by JpaRepository
}
