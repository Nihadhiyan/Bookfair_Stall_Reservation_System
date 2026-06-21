package com.bookfair.backend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LayoutPositionDto {
    private Integer xCoord;
    private Integer yCoord;
    private Integer width;
    private Integer height;
    // private Integer rotation;
    // private Integer zIndex;
}
