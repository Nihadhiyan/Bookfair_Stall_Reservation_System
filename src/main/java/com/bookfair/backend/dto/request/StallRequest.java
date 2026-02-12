package com.bookfair.backend.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class StallRequest {
    private String name;       
    private String size;        
    private BigDecimal price;
    private String status;      
    private Integer xCoord;
    private Integer yCoord;
}
