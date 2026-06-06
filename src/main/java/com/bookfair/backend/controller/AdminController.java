package com.bookfair.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.admin.response.AdminDashboardResponse;
import com.bookfair.backend.service.AdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {  
    private final AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }
    
}
