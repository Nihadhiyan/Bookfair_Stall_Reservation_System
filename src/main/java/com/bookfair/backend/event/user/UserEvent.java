package com.bookfair.backend.event.user;

import java.util.UUID;

public interface UserEvent {
    /** @return The ID of the user involved in this event */
    UUID userId();
    String email();
}