package com.bookfair.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookfair.backend.model.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {

    List<Building> findByVenueIdAndActiveTrue(UUID venueId);
    
}
