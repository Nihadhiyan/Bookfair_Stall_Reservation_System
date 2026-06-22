package com.bookfair.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.building.response.BuildingResponse;
import com.bookfair.backend.dto.venue.request.CreateVenueRequest;
import com.bookfair.backend.dto.venue.request.UpdateVenueRequest;
import com.bookfair.backend.dto.venue.response.VenueMapResponse;
import com.bookfair.backend.dto.venue.response.VenueResponse;
import com.bookfair.backend.service.VenueService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/venues")
public class VenueController {

    private final VenueService venueService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VenueResponse> createVenue (@RequestBody @Valid CreateVenueRequest request) {
        ResponseEntity.status(HttpStatus.CREATED).body(venueService.createVenue(request));
    }

    @GetMapping
    public ResponseEntity<Page<VenueResponse>> getAllVenues (@PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        ResponseEntity.ok(venueService.getAllVenues(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getVenue (@PathVariable UUID id) {
        ResponseEntity.ok(venueService.getVenue(id));
    }

    @GetMapping("/{id}/map")
    public ResponseEntity<VenueMapResponse> getVenueMap(@PathVariable UUID id) {
        return ResponseEntity.ok(venueService.getVenueMap(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueResponse> updateVenue(@PathVariable UUID id, @Valid @RequestBody UpdateVenueRequest request) {
        return ResponseEntity.ok(venueService.updateVenue(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable UUID id) {
        venueService.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{venueId}/buildings")
    public ResponseEntity<List<BuildingResponse>> getBuildingsByVenue(@PathVariable UUID venueId) {
        return ResponseEntity.ok(venueService.getBuildingsByVenue(venueId));
    }

    @GetMapping("/{venueId}/markers")
    public ResponseEntity<?> getVenueMarkers(@PathVariable UUID venueId) {
        return ResponseEntity.ok(venueService.getMarkersByVenue(venueId));
    }


}
