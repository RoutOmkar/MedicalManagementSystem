package com.hospital.service;

import com.hospital.entity.Prescription;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrescriptionService {

    Prescription createPrescription(Prescription prescription);

    List<Prescription> getAllPrescriptions();

    Optional<Prescription> getPrescriptionById(Integer id);

    Prescription updatePrescription(Prescription prescription); // ID included in the object

    void deletePrescription(Integer id);

    List<Prescription> getPrescriptionsByDoctor(Integer doctorId);

    List<Prescription> getPrescriptionsByPatient(Integer patientId);

    List<Prescription> getPrescriptionsByAppointment(Integer appointmentId);

    List<Prescription> getPrescriptionsByDate(LocalDate date);

    boolean existsById(Integer id);
}
