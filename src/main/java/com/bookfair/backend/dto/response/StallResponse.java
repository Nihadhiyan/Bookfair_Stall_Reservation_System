package com.bookfair.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class StallResponse {
    private Long id;
    private String name;      
    private String size;     
    private BigDecimal price; 
    private String status;    
    private Integer xCoord;   
    private Integer yCoord;
}
