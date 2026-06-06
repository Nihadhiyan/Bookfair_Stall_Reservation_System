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
public class LayoutMarkerDto {
    private UUID id;

    private String label;

    private String type;

    private Boolean primaryMarker;

    private Boolean active;
    
    private LayoutPositionDto layout;
}
