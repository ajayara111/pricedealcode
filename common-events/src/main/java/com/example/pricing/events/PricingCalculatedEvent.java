package com.example.pricing.events;

import java.math.BigDecimal;

public record PricingCalculatedEvent(String dealId, BigDecimal revenue, BigDecimal margin, boolean approvalRequired) {
}
