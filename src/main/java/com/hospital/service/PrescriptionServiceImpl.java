package com.hospital.service;

import com.hospital.entity.Prescription;
import com.hospital.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Integer id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public Prescription updatePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public void deletePrescription(Integer id) {
        prescriptionRepository.deleteById(id);
    }

    @Override
    public List<Prescription> getPrescriptionsByDoctor(Integer doctorId) {
        return prescriptionRepository.findByDoctorDrId(doctorId);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatient(Integer patientId) {
        return prescriptionRepository.findByPatientPId(patientId);
    }

    @Override
    public List<Prescription> getPrescriptionsByAppointment(Integer appointmentId) {
        return prescriptionRepository.findByAppointmentApId(appointmentId);
    }

    @Override
    public List<Prescription> getPrescriptionsByDate(LocalDate date) {
        return prescriptionRepository.findByDate(date);
    }

    @Override
    public boolean existsById(Integer id) {
        return prescriptionRepository.existsById(id);
    }
}
