package com.example.pricing.pricing;

import java.math.BigDecimal;
import java.util.List;

record PricingRequest(String dealId, BigDecimal assetsUnderManagement, long transactionVolume,
                      BigDecimal discountPercent, List<String> products) {
}

record PricingResult(String dealId, BigDecimal baseFee, BigDecimal finalRevenue, BigDecimal estimatedCost,
                     BigDecimal profit, BigDecimal marginPercent, boolean approvalRequired) {
}
