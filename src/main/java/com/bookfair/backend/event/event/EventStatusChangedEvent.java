package com.bookfair.backend.event.event;

import java.util.UUID;

public record EventStatusChangedEvent(UUID eventId, String oldStatus, String newStatus) {}
