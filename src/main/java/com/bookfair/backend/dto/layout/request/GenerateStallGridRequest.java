package com.bookfair.backend.dto.layout.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateStallGridRequest {

    @NotNull
    @Min(1)
    private Integer rows;

    @NotNull
    @Min(1)
    private Integer columns;

    @NotNull
    @Min(1)
    private Integer stallWidth;

    @NotNull
    @Min(1)
    private Integer stallLength;

    @NotNull
    @Min(0)
    private Integer aisleWidth;

    @NotNull
    @Min(0)
    private Integer startX;

    @NotNull
    @Min(0)
    private Integer startY;
}
