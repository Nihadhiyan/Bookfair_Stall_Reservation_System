package com.bookfair.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.response.AdminDashBoardResponse;
import com.bookfair.backend.service.AdminService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/admin")
public class AdminController {  
    private final AdminService adminService;
    
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashBoardResponse> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    @PutMapping("/promote/{id}")
    public ResponseEntity<String> promoteToAdmin(@PathVariable Long id) {
        return ResponseEntity.ok( adminService.promoteToAdmin(id));
    }
    
}
