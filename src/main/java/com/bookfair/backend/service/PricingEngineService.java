package com.bookfair.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookfair.backend.dto.pricing.request.PricingRuleRequest;
import com.bookfair.backend.dto.pricing.response.PricingBreakdownResponse;
import com.bookfair.backend.dto.pricing.response.PricingRuleResponse;
import com.bookfair.backend.dto.pricing.response.StallPricingResponse;
import com.bookfair.backend.model.PricingRule;
import com.bookfair.backend.model.Stall;
import com.bookfair.backend.repository.PricingRuleRepository;
import com.bookfair.backend.repository.StallRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingEngineService {

    private final PricingRuleRepository pricingRuleRepository;
    private final StallRepository stallRepository;

    @Transactional(readOnly = true)
    public PricingBreakdownResponse calculateQuote(List<UUID> stallIds, int durationDays, String orgType) {
        List<Stall> stalls = stallRepository.findAllById(stallIds);
        
        List<PricingRule> activeRules = pricingRuleRepository.findAllByActiveTrue();
        
        BigDecimal subtotal = BigDecimal.ZERO;
        List<StallPricingResponse> stallPricings = new ArrayList<>();

        for (Stall stall : stalls) {
            BigDecimal basePrice = BigDecimal.valueOf(stall.getAreaSquareFootage() * 10.0); // simple base logic
            BigDecimal currentPrice = basePrice.multiply(BigDecimal.valueOf(durationDays));

            for (PricingRule rule : activeRules) {
                if ("ORG_TYPE".equalsIgnoreCase(rule.getConditionType()) && rule.getConditionValue().equalsIgnoreCase(orgType)) {
                    currentPrice = currentPrice.multiply(rule.getMultiplier());
                } else if ("DURATION".equalsIgnoreCase(rule.getConditionType())) {
                    if (rule.getConditionValue().equals(">7_DAYS") && durationDays > 7) {
                        currentPrice = currentPrice.multiply(rule.getMultiplier());
                    }
                }
            }

            subtotal = subtotal.add(currentPrice);

            StallPricingResponse spr = new StallPricingResponse();
            spr.setStallId(stall.getId());
            spr.setStallNumber(stall.getStallNumber());
            spr.setBasePrice(basePrice);
            spr.setCalculatedPrice(currentPrice);
            stallPricings.add(spr);
        }

        BigDecimal discountAmount = BigDecimal.ZERO; // Optional complex logic
        BigDecimal taxAmount = subtotal.multiply(BigDecimal.valueOf(0.1)); // 10% tax
        BigDecimal total = subtotal.subtract(discountAmount).add(taxAmount);

        PricingBreakdownResponse response = new PricingBreakdownResponse();
        response.setStalls(stallPricings);
        response.setSubtotal(subtotal);
        response.setDiscountAmount(discountAmount);
        response.setTaxAmount(taxAmount);
        response.setTotal(total);
        response.setCurrency("USD");

        return response;
    }

    @Transactional(readOnly = true)
    public List<PricingRuleResponse> getActiveRules() {
        return pricingRuleRepository.findAllByActiveTrue().stream().map(rule -> {
            PricingRuleResponse response = new PricingRuleResponse();
            response.setId(rule.getId());
            response.setName(rule.getName());
            response.setDescription(rule.getDescription());
            response.setConditionType(rule.getConditionType());
            response.setConditionValue(rule.getConditionValue());
            response.setMultiplier(rule.getMultiplier());
            response.setActive(rule.getActive());
            return response;
        }).collect(Collectors.toList());
    }

    @Transactional
    public PricingRuleResponse createPricingRule(PricingRuleRequest request) {
        PricingRule rule = new PricingRule();
        rule.setName(request.getName());
        rule.setDescription(request.getDescription());
        rule.setConditionType(request.getConditionType());
        rule.setConditionValue(request.getConditionValue());
        rule.setMultiplier(request.getMultiplier());
        rule.setActive(true);

        PricingRule saved = pricingRuleRepository.save(rule);

        PricingRuleResponse response = new PricingRuleResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setDescription(saved.getDescription());
        response.setConditionType(saved.getConditionType());
        response.setConditionValue(saved.getConditionValue());
        response.setMultiplier(saved.getMultiplier());
        response.setActive(saved.getActive());

        log.info("Created new pricing rule: {}", saved.getName());
        return response;
    }
}
