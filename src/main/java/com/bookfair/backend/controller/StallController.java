package com.bookfair.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.venue.request.CreateStallRequest;
import com.bookfair.backend.dto.venue.request.UpdateStallRequest;
import com.bookfair.backend.dto.venue.response.StallResponse;
import com.bookfair.backend.service.StallService;

import lombok.RequiredArgsConstructor;


@RestController 
@RequiredArgsConstructor
@RequestMapping("/api/v1/stalls")
public class StallController {
    private final StallService stallService;
    
    @PostMapping
    public ResponseEntity<List<StallResponse>> createStall(@RequestBody List<CreateStallRequest> stallRequests) {
        return ResponseEntity.ok(stallService.createStall(stallRequests));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StallResponse> getStallById(@PathVariable UUID id) {
        return ResponseEntity.ok(stallService.getStallById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<StallResponse> updateStall(@PathVariable UUID id, @RequestBody UpdateStallRequest stallRequest) {
        return ResponseEntity.ok(stallService.updateStall(id, stallRequest));
    }

    @GetMapping("/available")
    public ResponseEntity<List<StallResponse>> getAvailableStalls() {
        return ResponseEntity.ok(stallService.getAvailableStalls());
    }

}
