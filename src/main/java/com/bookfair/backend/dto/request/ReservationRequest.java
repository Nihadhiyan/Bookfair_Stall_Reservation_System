package com.bookfair.backend.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private String date; 
    private String time;
    private Long userId;
    private List<Long> stallId;
}
