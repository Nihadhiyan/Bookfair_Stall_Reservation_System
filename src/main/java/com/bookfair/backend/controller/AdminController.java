package com.bookfair.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.admin.response.AdminDashboardResponse;
import com.bookfair.backend.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ORG_ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboardMetrics() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/system/maintenance")
    public ResponseEntity<String> toggleMaintenanceMode() {
        adminService.toggleMaintenanceMode();
        return ResponseEntity.ok("Maintenance mode is now: " + adminService.isMaintenanceMode());
    }
}
