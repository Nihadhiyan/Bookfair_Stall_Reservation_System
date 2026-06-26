package com.bookfair.backend.event.reservation;

import java.util.UUID;

public record ReservationRefundedEvent(UUID userId, String username, String email, UUID reservationId, String eventName) implements ReservationEvent {
}
