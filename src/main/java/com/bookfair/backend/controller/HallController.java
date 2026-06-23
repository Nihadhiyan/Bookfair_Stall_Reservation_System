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
import com.bookfair.backend.dto.hall.request.CreateHallRequest;
import com.bookfair.backend.dto.hall.request.UpdateHallRequest;
import com.bookfair.backend.dto.hall.response.HallLayoutResponse;
import com.bookfair.backend.dto.hall.response.HallResponse;
import com.bookfair.backend.dto.layout.request.GenerateStallGridRequest;
import com.bookfair.backend.dto.stall.response.StallResponse;
import com.bookfair.backend.service.HallService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/halls")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class HallController {

    private final HallService hallService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<HallResponse> createHall(@Valid @RequestBody CreateHallRequest request) {
        HallResponse data = hallService.createHall(request);
        return new ApiResponseDto<>(true, "Hall created successfully", data, LocalDateTime.now());
    }

    @GetMapping("/{id}")
    public ApiResponseDto<HallResponse> getHallById(@PathVariable UUID id) {
        HallResponse data = hallService.getHallById(id);
        return new ApiResponseDto<>(true, "Hall fetched successfully", data, LocalDateTime.now());
    }

    @GetMapping("/{id}/layout")
    public ApiResponseDto<HallLayoutResponse> getHallLayout(@PathVariable UUID id) {
        HallLayoutResponse data = hallService.getHallLayout(id);
        return new ApiResponseDto<>(true, "Hall layout fetched successfully", data, LocalDateTime.now());
    }

    @PutMapping("/{id}")
    public ApiResponseDto<HallResponse> updateHall(@PathVariable UUID id, @Valid @RequestBody UpdateHallRequest request) {
        HallResponse data = hallService.updateHall(id, request);
        return new ApiResponseDto<>(true, "Hall updated successfully", data, LocalDateTime.now());
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto<Void> deleteHall(@PathVariable UUID id) {
        hallService.deleteHall(id);
        return new ApiResponseDto<>(true, "Hall deleted successfully", null, LocalDateTime.now());
    }

    @GetMapping("/{id}/stalls")
    public ApiResponseDto<List<StallResponse>> getStallsByHall(@PathVariable UUID id) {
        List<StallResponse> data = hallService.getStallsByHall(id);
        return new ApiResponseDto<>(true, "Stalls fetched successfully", data, LocalDateTime.now());
    }

    @PostMapping("/{id}/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<List<StallResponse>> generateStallGrid(@PathVariable UUID id, @Valid @RequestBody GenerateStallGridRequest request) {
        List<StallResponse> data = hallService.generateStallGrid(id, request);
        return new ApiResponseDto<>(true, "Stall grid generated successfully", data, LocalDateTime.now());
    }
}
