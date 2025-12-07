package com.hospital.controller;

import com.hospital.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardStats() {
        return analyticsService.getDashboardStats();
    }

    @GetMapping("/patient/{patientId}/stats")
    public Map<String, Object> getPatientStats(@PathVariable Integer patientId) {
        return analyticsService.getPatientStats(patientId);
    }

    @GetMapping("/doctor/{doctorId}/stats")
    public Map<String, Object> getDoctorStats(@PathVariable Integer doctorId) {
        return analyticsService.getDoctorStats(doctorId);
    }

    @GetMapping("/admin/stats")
    public Map<String, Object> getAdminStats() {
        return analyticsService.getAdminStats();
    }
}
