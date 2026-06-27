package com.bookfair.backend.dto.reservation.request;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {

    @NotNull(message = "Event id is required")
    private UUID eventId;

    @NotEmpty(message = "At least one stall id is required")
    private List<UUID> stallIds;

    @NotNull(message = "Reservation start time is required")
    private Instant reservationStartDateTime;

    @NotNull(message = "Expiration time is required")
    private Instant expiresAt;

    @NotNull(message = "Genre id is required")
    private UUID genreId;

    @NotNull(message = "Organization id is required")
    private UUID organizationId;

}
