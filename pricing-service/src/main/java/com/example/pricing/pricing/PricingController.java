package com.example.pricing.pricing;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.*;

@RestController
@RequestMapping("/api/pricing")
class PricingController {
    private final PricingRuleEngine engine;

    PricingController(PricingRuleEngine e) {
        engine = e;
    }

    @PostMapping("/calculate")
    PricingResult calculate(@RequestBody PricingRequest r) {
        BigDecimal revenue = engine.calculate(r);
        BigDecimal cost = revenue.multiply(new BigDecimal("0.72")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal profit = revenue.subtract(cost);
        BigDecimal margin = profit.multiply(new BigDecimal("100")).divide(revenue, 2, RoundingMode.HALF_UP);
        boolean approval = margin.compareTo(new BigDecimal("20")) < 0 || r.discountPercent().compareTo(new BigDecimal("10")) > 0;
        return new PricingResult(r.dealId(), revenue.divide(BigDecimal.ONE.subtract(r.discountPercent().divide(new BigDecimal("100"), 8, RoundingMode.HALF_UP)), 2, RoundingMode.HALF_UP), revenue, cost, profit, margin, approval);
    }
}
