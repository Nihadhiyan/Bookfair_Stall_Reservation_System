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
import com.bookfair.backend.dto.building.request.CreateBuildingRequest;
import com.bookfair.backend.dto.building.request.UpdateBuildingRequest;
import com.bookfair.backend.dto.building.response.BuildingResponse;
import com.bookfair.backend.dto.floor.response.FloorResponse;
import com.bookfair.backend.service.BuildingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/buildings")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<BuildingResponse> createBuilding(@Valid @RequestBody CreateBuildingRequest request) {
        BuildingResponse data = buildingService.createBuilding(request);
        return new ApiResponseDto<BuildingResponse>(true, "Building created successfully", data, LocalDateTime.now());
    }

    @GetMapping("/{id}")
    public ApiResponseDto<BuildingResponse> getBuildingById(@PathVariable UUID id) {
        BuildingResponse data = buildingService.getBuildingById(id);
        return new ApiResponseDto<BuildingResponse>(true, "Building fetched successfully", data, LocalDateTime.now());
    }

    @PutMapping("/{id}")
    public ApiResponseDto<BuildingResponse> updateBuilding(@PathVariable UUID id, @Valid @RequestBody UpdateBuildingRequest request) {
        BuildingResponse data = buildingService.updateBuilding(id, request);
        return new ApiResponseDto<BuildingResponse>(true, "Building updated successfully", data, LocalDateTime.now());
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto<Void> deleteBuilding(@PathVariable UUID id) {
        buildingService.deleteBuilding(id);
        return new ApiResponseDto<Void>(true, "Building deleted successfully", null, LocalDateTime.now());
    }

    @GetMapping("/{id}/floors")
    public ApiResponseDto<List<FloorResponse>> getFloorsByBuilding(@PathVariable UUID id) {
        List<FloorResponse> data = buildingService.getFloorsByBuilding(id);
        return new ApiResponseDto<List<FloorResponse>>(true, "Floors fetched successfully", data, LocalDateTime.now());
    }
}
