package com.bookfair.backend.dto.pricing.request;

import java.math.BigDecimal;

import com.bookfair.backend.model.PricingRule.ConditionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PricingRuleRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private ConditionType conditionType;

    @NotBlank
    private String conditionValue;

    @NotNull
    private BigDecimal multiplier;
}
