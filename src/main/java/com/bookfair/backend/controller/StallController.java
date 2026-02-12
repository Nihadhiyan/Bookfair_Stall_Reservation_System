package com.bookfair.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.request.StallRequest;
import com.bookfair.backend.dto.response.StallResponse;
import com.bookfair.backend.service.StallService;


@RestController 
@RequestMapping("/api/stalls")
public class StallController {
    private final StallService stallService;

    public StallController(StallService stallService) {
        this.stallService = stallService;
    }

    @GetMapping
    public ResponseEntity<List<StallResponse>> getAllStalls() {
        return ResponseEntity.ok(stallService.getAllStalls());
    }
    
    @PostMapping
    public ResponseEntity<StallResponse> createStall(@RequestBody StallRequest stallRequest) {
        return ResponseEntity.ok(stallService.createStall(stallRequest));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StallResponse> getStallById(@PathVariable Long id) {
        return ResponseEntity.ok(stallService.getStallById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<StallResponse> updateStall(@PathVariable Long id, @RequestBody StallRequest stallRequest) {
        return ResponseEntity.ok(stallService.updateStall(id, stallRequest));
    }

    @GetMapping("/available")
    public ResponseEntity<List<StallResponse>> getAvailableStalls() {
        return ResponseEntity.ok(stallService.getAvailableStalls());
    }

}
