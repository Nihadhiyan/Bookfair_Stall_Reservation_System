package com.bookfair.backend.event.reservation;

import java.util.UUID;

public record ReservationRefundPendingEvent(UUID userId, String username, String email, UUID reservationId, String eventName) implements ReservationEvent {
}
