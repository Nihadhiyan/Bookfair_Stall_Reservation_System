package com.bookfair.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookfair.backend.Model.entity.Stall;

import java.util.List;
import java.util.Optional;

@Repository
public interface StallRepository extends JpaRepository<Stall, Long> {
    
    List<Stall> findByStatus(Stall.StallStatus status);
    
    Optional<Stall> findByXCoordAndYCoord(Integer xCoord, Integer yCoord);
    
    @Query("SELECT s FROM Stall s WHERE s.status = 'AVAILABLE' ORDER BY s.xCoord, s.yCoord")
    List<Stall> findAvailableStallsOrderedByPosition();
    
    boolean existsByXCoordAndYCoord(Integer xCoord, Integer yCoord);
    
    Optional<Stall> findById(Long id);
}
