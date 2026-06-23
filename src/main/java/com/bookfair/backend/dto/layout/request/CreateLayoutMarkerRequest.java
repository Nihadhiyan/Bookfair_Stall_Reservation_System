package com.bookfair.backend.dto.layout.request;

import java.util.UUID;

import com.bookfair.backend.dto.common.LayoutPositionDto;
import com.bookfair.backend.model.LayoutMarker;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLayoutMarkerRequest {

    private UUID venueId;

    private UUID buildingId;

    private UUID hallId;

    @NotNull
    private LayoutMarker.FeatureType type;

    @NotBlank
    private String label;

    @NotNull
    private Boolean primaryMarker;

    @Valid
    @NotNull
    private LayoutPositionDto layout;
}
