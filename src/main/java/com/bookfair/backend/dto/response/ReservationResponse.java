package com.bookfair.backend.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private Long id;
    private String date;
    private String time;
    private String userId;
    private List<Long> stallId;
    private String status;
    private Long genreId;
} 
