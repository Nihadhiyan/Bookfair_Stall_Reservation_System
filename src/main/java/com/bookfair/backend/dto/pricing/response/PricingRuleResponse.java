package com.bookfair.backend.dto.pricing.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PricingRuleResponse {
    private UUID id;
    private String name;
    private String description;
    private String conditionType;
    private String conditionValue;
    private BigDecimal multiplier;
    private Boolean active;
}
