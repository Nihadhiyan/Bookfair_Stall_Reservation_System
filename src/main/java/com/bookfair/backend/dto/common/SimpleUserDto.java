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
public class SimpleUserDto {
    private UUID id;

    private String username;
    
    private String email;
}
