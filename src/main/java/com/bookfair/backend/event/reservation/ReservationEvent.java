package com.bookfair.backend.event.reservation;

import java.util.UUID;
import com.bookfair.backend.event.user.UserEvent;

public interface ReservationEvent extends UserEvent {
    UUID reservationId();
    String username();
}
