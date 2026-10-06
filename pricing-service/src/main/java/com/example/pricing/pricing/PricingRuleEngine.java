package com.example.pricing.pricing;

import org.springframework.stereotype.Component;

import java.math.*;

@Component
class PricingRuleEngine {
    BigDecimal calculate(PricingRequest r) {
        // Demo: 8 bps first $100M, 6 bps next $400M, 4 bps above $500M + $0.02/transaction.
        BigDecimal aum = r.assetsUnderManagement(), fee = BigDecimal.ZERO;
        BigDecimal first = aum.min(new BigDecimal("100000000"));
        fee = fee.add(first.multiply(new BigDecimal("0.0008")));
        if (aum.compareTo(new BigDecimal("100000000")) > 0) {
            BigDecimal next = aum.subtract(new BigDecimal("100000000")).min(new BigDecimal("400000000"));
            fee = fee.add(next.multiply(new BigDecimal("0.0006")));
        }
        if (aum.compareTo(new BigDecimal("500000000")) > 0)
            fee = fee.add(aum.subtract(new BigDecimal("500000000")).multiply(new BigDecimal("0.0004")));
        fee = fee.add(new BigDecimal(r.transactionVolume()).multiply(new BigDecimal("0.02")));
        BigDecimal factor = BigDecimal.ONE.subtract(r.discountPercent().divide(new BigDecimal("100"), 8, RoundingMode.HALF_UP));
        if (r.products() != null && r.products().size() > 1)
            factor = factor.multiply(new BigDecimal("0.97")); // 3% bundle adjustment
        return fee.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }
}
