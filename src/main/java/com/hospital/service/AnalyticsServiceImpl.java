package com.hospital.service;

import com.hospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Basic counts
        stats.put("totalPatients", patientRepository.count());
        stats.put("totalDoctors", doctorRepository.count());
        stats.put("totalAppointments", appointmentRepository.count());
        stats.put("totalPrescriptions", prescriptionRepository.count());
        stats.put("totalSpecializations", specializationRepository.count());
        
        // Today's appointments
        stats.put("todayAppointments", appointmentRepository.findByAppointmentDate(LocalDate.now()).size());
        
        // Pending appointments
        stats.put("pendingAppointments", appointmentRepository.findByStatus("PENDING").size());
        
        return stats;
    }

    @Override
    public Map<String, Object> getPatientStats(Integer patientId) {
        Map<String, Object> stats = new HashMap<>();
        
        // Patient's appointments
        stats.put("totalAppointments", appointmentRepository.findByPatientPId(patientId).size());
        stats.put("pendingAppointments", appointmentRepository.findByPatientPId(patientId)
            .stream().filter(a -> "PENDING".equals(a.getStatus())).count());
        
        // Patient's prescriptions
        stats.put("totalPrescriptions", prescriptionRepository.findByPatientPId(patientId).size());
        
        return stats;
    }

    @Override
    public Map<String, Object> getDoctorStats(Integer doctorId) {
        Map<String, Object> stats = new HashMap<>();
        
        // Doctor's appointments
        stats.put("totalAppointments", appointmentRepository.findByDoctorDrId(doctorId).size());
        stats.put("todayAppointments", appointmentRepository.findTodayAppointments(doctorId).size());
        stats.put("upcomingAppointments", appointmentRepository.findUpcomingAppointments(doctorId).size());
        stats.put("pendingAppointments", appointmentRepository.findByDoctorDrId(doctorId)
            .stream().filter(a -> "PENDING".equals(a.getStatus())).count());
        
        // Doctor's prescriptions
        stats.put("totalPrescriptions", prescriptionRepository.findByDoctorDrId(doctorId).size());
        
        return stats;
    }

    @Override
    public Map<String, Object> getAdminStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // System-wide statistics
        stats.put("totalPatients", patientRepository.count());
        stats.put("totalDoctors", doctorRepository.count());
        stats.put("totalAppointments", appointmentRepository.count());
        stats.put("totalPrescriptions", prescriptionRepository.count());
        stats.put("totalSpecializations", specializationRepository.count());
        
        // Status breakdowns
        stats.put("pendingAppointments", appointmentRepository.findByStatus("PENDING").size());
        stats.put("confirmedAppointments", appointmentRepository.findByStatus("CONFIRMED").size());
        stats.put("completedAppointments", appointmentRepository.findByStatus("COMPLETED").size());
        
        // Today's activity
        stats.put("todayAppointments", appointmentRepository.findByAppointmentDate(LocalDate.now()).size());
        
        return stats;
    }
}
