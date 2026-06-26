package com.bookfair.backend.event.user;

import java.util.UUID;

public record UserRoleUpdatedEvent (
        UUID userId,
        String username,
        String email,
        String oldRole,
        String newRole) implements UserEvent {
}