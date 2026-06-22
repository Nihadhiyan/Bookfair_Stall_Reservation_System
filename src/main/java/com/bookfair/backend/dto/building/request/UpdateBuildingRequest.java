package com.bookfair.backend.dto.building.request;

import java.util.UUID;

import com.bookfair.backend.dto.common.LayoutPositionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBuildingRequest {
    @NotNull(message = "Venue id is required")
    private UUID venueId;

    @NotBlank(message = "Name is required")
    private String name;

    @Valid
    @NotNull(message = "Layout position is required")
    private LayoutPositionDto layoutPosition;

    @NotNull(message = "Square footage is required")
    private Double squareFootage;

    @NotBlank(message = "Type is required")
    private String type;
    
    @NotNull(message = "Active is required")
    private Boolean active;

}
