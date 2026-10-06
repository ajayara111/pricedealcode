package com.example.pricing.policy;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.*;
import java.util.*;

@RestController
@RequestMapping("/api/policy")
class PolicyController {
    @PostMapping("/evaluate")
    Decision evaluate(@RequestBody Request r) {
        var a = new LinkedHashSet<String>();
        var v = new ArrayList<String>();
        if (r.marginPercent().compareTo(new BigDecimal("20")) < 0) {
            a.add("FINANCE");
            v.add("MIN_MARGIN");
        }
        if (r.discountPercent().compareTo(new BigDecimal("10")) > 0) {
            a.add("PRODUCT");
            v.add("DISCOUNT_LIMIT");
        }
        if (r.discountPercent().compareTo(new BigDecimal("15")) > 0) {
            a.add("RISK");
            v.add("HIGH_DISCOUNT");
        }
        return new Decision(!a.isEmpty(), List.copyOf(a), v);
    }

    record Request(BigDecimal marginPercent, BigDecimal discountPercent) {
    }

    record Decision(boolean approvalRequired, List<String> approvers, List<String> violations) {
    }
}
