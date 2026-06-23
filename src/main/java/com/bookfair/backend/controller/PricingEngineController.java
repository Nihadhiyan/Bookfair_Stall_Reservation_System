package com.bookfair.backend.controller;

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

    @GetMapping("/quote")
    @PreAuthorize("hasAnyRole('USER', 'ORG_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PricingBreakdownResponse> calculateQuote(
            @RequestParam List<UUID> stallIds,
            @RequestParam int durationDays,
            @RequestParam String orgType) {
        return ResponseEntity.ok(pricingEngineService.calculateQuote(stallIds, durationDays, orgType));
    }

    @GetMapping("/rules")
    @PreAuthorize("hasAnyRole('USER', 'ORG_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<PricingRuleResponse>> viewActiveRules() {
        return ResponseEntity.ok(pricingEngineService.getActiveRules());
    }

    @PostMapping("/rules")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<PricingRuleResponse> addPricingRule(@Valid @RequestBody PricingRuleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pricingEngineService.createPricingRule(request));
    }
}
