package com.bookfair.backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashBoardResponse {
    private long totalUsers;
    private long totalStalls;
    private long activeReservations;
}
