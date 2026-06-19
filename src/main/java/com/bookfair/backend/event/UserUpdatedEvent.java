package com.bookfair.backend.event;

import java.util.UUID;


public record UserUpdatedEvent(
    UUID userId,
    String username
) {}
