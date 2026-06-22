package com.bookfair.backend.service;

import org.springframework.stereotype.Service;

import com.bookfair.backend.dto.venue.mapper.VenueMapper;
import com.bookfair.backend.dto.venue.request.CreateVenueRequest;
import com.bookfair.backend.dto.venue.response.VenueResponse;
import com.bookfair.backend.exception.DuplicateResourceException;
import com.bookfair.backend.exception.ErrorCode;
import com.bookfair.backend.repository.VenueRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueService {
    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

     @Transactional
    public VenueResponse createVenue(CreateVenueRequest request) {
        if (venueRepository.existsByNameAndActiveTrue(request.getName())) {
            throw new DuplicateResourceException(
                "A venue with this name already exists.",
                ErrorCode.BUSINESS_RULE_VIOLATION
            );
        }

        venueMapper.to
    }

}
