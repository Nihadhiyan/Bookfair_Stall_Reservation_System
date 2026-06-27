package com.bookfair.backend.controller;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.common.ApiResponseDto;
import com.bookfair.backend.dto.pricing.request.PricingRuleRequest;
import com.bookfair.backend.dto.pricing.response.PricingBreakdownResponse;
import com.bookfair.backend.dto.pricing.response.PricingRuleResponse;
import com.bookfair.backend.service.PricingEngineService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pricing")
@RequiredArgsConstructor
public class PricingEngineController {

    private final PricingEngineService pricingEngineService;
    private final com.bookfair.backend.service.PricingRuleService pricingRuleService;

    @GetMapping("/quote")
    @PreAuthorize("hasAnyRole('USER', 'ORG_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponseDto<PricingBreakdownResponse>> calculateQuote(
            @RequestParam List<UUID> stallIds,
            @RequestParam int durationDays,
            @RequestParam String orgType) {
        PricingBreakdownResponse data = pricingEngineService.calculateQuote(stallIds, durationDays, orgType);
        return ResponseEntity.ok(new ApiResponseDto<>(true, "Quote calculated successfully", data, Instant.now()));
    }

    @GetMapping("/rules")
    @PreAuthorize("hasAnyRole('USER', 'ORG_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponseDto<List<PricingRuleResponse>>> viewActiveRules() {
        List<PricingRuleResponse> data = pricingRuleService.getActiveRules();
        return ResponseEntity.ok(new ApiResponseDto<>(true, "Active pricing rules fetched successfully", data, Instant.now()));
    }

    @PostMapping("/rules")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponseDto<PricingRuleResponse>> addPricingRule(@Valid @RequestBody PricingRuleRequest request) {
        PricingRuleResponse data = pricingRuleService.createPricingRule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(true, "Pricing rule created successfully", data, Instant.now()));
    }
}
