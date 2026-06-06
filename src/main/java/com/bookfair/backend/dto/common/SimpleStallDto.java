package com.bookfair.backend.dto.common;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleStallDto {
    private UUID id;

    private String name;

    private String stallType;
    
    private Double squareFootage;
}
