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

import com.bookfair.backend.dto.ApiResponseDto;
import com.bookfair.backend.dto.floor.request.CreateFloorRequest;
import com.bookfair.backend.dto.floor.request.UpdateFloorRequest;
import com.bookfair.backend.dto.floor.response.FloorResponse;
import com.bookfair.backend.dto.hall.response.HallResponse;
import com.bookfair.backend.service.FloorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/floors")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class FloorController {

    private final FloorService floorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<FloorResponse> createFloor(@Valid @RequestBody CreateFloorRequest request) {
        FloorResponse data = floorService.createFloor(request);
        return new ApiResponseDto<>(true, "Floor created successfully", data, LocalDateTime.now());
    }

    @GetMapping("/{id}")
    public ApiResponseDto<FloorResponse> getFloorById(@PathVariable UUID id) {
        FloorResponse data = floorService.getFloorById(id);
        return new ApiResponseDto<>(true, "Floor fetched successfully", data, LocalDateTime.now());
    }

    @PutMapping("/{id}")
    public ApiResponseDto<FloorResponse> updateFloor(@PathVariable UUID id, @Valid @RequestBody UpdateFloorRequest request) {
        FloorResponse data = floorService.updateFloor(id, request);
        return new ApiResponseDto<>(true, "Floor updated successfully", data, LocalDateTime.now());
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto<Void> deleteFloor(@PathVariable UUID id) {
        floorService.deleteFloor(id);
        return new ApiResponseDto<>(true, "Floor deleted successfully", null, LocalDateTime.now());
    }

    @GetMapping("/{id}/halls")
    public ApiResponseDto<List<HallResponse>> getHallsByFloor(@PathVariable UUID id) {
        List<HallResponse> data = floorService.getHallsByFloor(id);
        return new ApiResponseDto<>(true, "Halls fetched successfully", data, LocalDateTime.now());
    }
}
