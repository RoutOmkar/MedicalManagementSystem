package com.hospital.service;

import java.util.Map;

public interface AnalyticsService {
    Map<String, Object> getDashboardStats();
    Map<String, Object> getPatientStats(Integer patientId);
    Map<String, Object> getDoctorStats(Integer doctorId);
    Map<String, Object> getAdminStats();
}
