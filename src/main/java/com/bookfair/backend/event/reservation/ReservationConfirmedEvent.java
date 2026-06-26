package com.bookfair.backend.event.reservation;

import java.util.UUID;

public record ReservationConfirmedEvent(UUID userId, String username, String email, UUID reservationId, String eventName, String qrCodeBase64) implements ReservationEvent {
}
