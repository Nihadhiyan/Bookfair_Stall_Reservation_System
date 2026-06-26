package com.bookfair.backend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.common.ApiResponseDto;
import com.bookfair.backend.dto.common.LayoutMarkerDto;
import com.bookfair.backend.dto.layout.request.CreateLayoutMarkerRequest;
import com.bookfair.backend.dto.layout.request.UpdateLayoutMarkerRequest;
import com.bookfair.backend.service.LayoutMarkerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/layout-markers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class LayoutMarkerController {

    private final LayoutMarkerService layoutMarkerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<LayoutMarkerDto> createLayoutMarker(@Valid @RequestBody CreateLayoutMarkerRequest request) {
        LayoutMarkerDto data = layoutMarkerService.createLayoutMarker(request);
        return new ApiResponseDto<>(true, "Layout marker created successfully", data, LocalDateTime.now());
    }

    @PutMapping("/{id}")
    public ApiResponseDto<LayoutMarkerDto> updateLayoutMarker(@PathVariable UUID id, @Valid @RequestBody UpdateLayoutMarkerRequest request) {
        LayoutMarkerDto data = layoutMarkerService.updateLayoutMarker(id, request);
        return new ApiResponseDto<>(true, "Layout marker updated successfully", data, LocalDateTime.now());
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto<Void> deleteLayoutMarker(@PathVariable UUID id) {
        layoutMarkerService.deleteLayoutMarker(id);
        return new ApiResponseDto<>(true, "Layout marker deleted successfully", null, LocalDateTime.now());
    }

    @GetMapping("/venue/{id}")
    public ApiResponseDto<List<LayoutMarkerDto>> getMarkersByVenue(@PathVariable UUID id) {
        List<LayoutMarkerDto> data = layoutMarkerService.getMarkersByVenue(id);
        return new ApiResponseDto<>(true, "Venue markers fetched successfully", data, LocalDateTime.now());
    }

    @GetMapping("/building/{id}")
    public ApiResponseDto<List<LayoutMarkerDto>> getMarkersByBuilding(@PathVariable UUID id) {
        List<LayoutMarkerDto> data = layoutMarkerService.getMarkersByBuilding(id);
        return new ApiResponseDto<>(true, "Building markers fetched successfully", data, LocalDateTime.now());
    }

    @GetMapping("/hall/{id}")
    public ApiResponseDto<List<LayoutMarkerDto>> getMarkersByHall(@PathVariable UUID id) {
        List<LayoutMarkerDto> data = layoutMarkerService.getMarkersByHall(id);
        return new ApiResponseDto<>(true, "Hall markers fetched successfully", data, LocalDateTime.now());
    }
}
