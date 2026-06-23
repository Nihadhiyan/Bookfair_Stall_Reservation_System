package com.bookfair.backend.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookfair.backend.dto.admin.response.AdminDashboardResponse;
import com.bookfair.backend.repository.ReservationRepository;
import com.bookfair.backend.repository.StallRepository;
import com.bookfair.backend.repository.UserRepository;
import com.bookfair.backend.repository.PaymentRepository;
import com.bookfair.backend.model.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final StallRepository stallRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    // In-memory toggle for system maintenance mode
    private boolean maintenanceMode = false;

    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboardMetrics() {
        long totalUsers = userRepository.count();
        long totalStalls = stallRepository.count();
        long activeReservations = reservationRepository.count();
        
        BigDecimal totalRevenue = paymentRepository.findAll().stream()
                .filter(p -> p.getStatus() == Payment.PaymentStatus.COMPLETED)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        AdminDashboardResponse response = new AdminDashboardResponse();
        response.setTotalUsers(totalUsers);
        response.setTotalStalls(totalStalls);
        response.setActiveReservations(activeReservations);
        response.setTotalRevenue(totalRevenue);

        return response;
    }

    public void toggleMaintenanceMode() {
        this.maintenanceMode = !this.maintenanceMode;
        log.info("System maintenance mode toggled to: {}", this.maintenanceMode);
    }
    
    public boolean isMaintenanceMode() {
        return this.maintenanceMode;
    }
}