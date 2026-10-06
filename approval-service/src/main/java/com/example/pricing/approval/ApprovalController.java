package com.example.pricing.approval;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;

@RestController
@RequestMapping("/api/approvals")
class ApprovalController {
    private final Map<String, String> store = new ConcurrentHashMap<>();

    @PostMapping
    Approval create(@RequestBody Request r) {
        String id = UUID.randomUUID().toString();
        store.put(id, "PENDING");
        return new Approval(id, r.dealId(), r.approvers(), "PENDING");
    }

    @PostMapping("/{id}/approve")
    Map<String, String> approve(@PathVariable String id) {
        store.put(id, "APPROVED");
        return Map.of("id", id, "status", "APPROVED");
    }

    record Request(String dealId, List<String> approvers) {
    }

    record Approval(String id, String dealId, List<String> approvers, String status) {
    }
}
