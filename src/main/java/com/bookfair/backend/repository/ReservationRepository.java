package com.bookfair.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookfair.backend.Model.entity.Reservation;
import com.bookfair.backend.Model.entity.Stall;
import com.bookfair.backend.Model.entity.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    List<Reservation> findByUser(User user);
    
    List<Reservation> findByStallsContaining(Stall stall);
    
    List<Reservation> findByDate(LocalDate date);
    
    List<Reservation> findByUserAndStatus(User user, Reservation.ReservationStatus status);
    
    List<Reservation> findByStallsContainingAndDate(Stall stall, LocalDate date);

    List<Reservation> findByStatus(Reservation.ReservationStatus status);
}
