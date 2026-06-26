package com.bookfair.backend.event.reservation;

import java.util.UUID;

public record ReservationRequestReceivedEvent(UUID userId, String username, String email, UUID reservationId, String eventName) implements ReservationEvent {
}
