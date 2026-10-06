package com.example.pricing.profitability;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.*;

@RestController
@RequestMapping("/api/profitability")
class ProfitabilityController {
    @PostMapping("/calculate")
    Result calculate(@RequestBody Request r) {
        BigDecimal p = r.revenue().subtract(r.directCost()).subtract(r.operatingCost());
        return new Result(p, p.multiply(new BigDecimal("100")).divide(r.revenue(), 2, RoundingMode.HALF_UP));
    }

    record Request(BigDecimal revenue, BigDecimal directCost, BigDecimal operatingCost) {
    }

    record Result(BigDecimal profit, BigDecimal marginPercent) {
    }
}
