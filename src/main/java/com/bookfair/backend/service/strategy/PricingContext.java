package com.bookfair.backend.service.strategy;

import java.time.Instant;

public record PricingContext(
        int durationDays,
        String orgType,
        Instant eventStartDate
) {}
