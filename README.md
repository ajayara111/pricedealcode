# Pricing Studio Reference Implementation

A portfolio-ready Java 21 / Spring Boot reference implementation matching the Pricing Studio solution architecture.

## What this demonstrates

- Configurable tiered pricing and relationship discounts
- Multi-product bundle adjustment
- Profitability / margin calculation
- Policy thresholds separated from pricing calculation
- Approval service + BPMN example
- Shared domain event contract
- Oracle-oriented schema and Kafka/Oracle Docker Compose

## Modules

`pricing-service`, `profitability-service`, `policy-service`, `approval-service`, `common-events`.

## Demo scenario

A $500M Custody + Fund Administration deal is priced using AUM tiers and transaction fees. A 12% relationship discount
plus a bundle adjustment is applied. Policy evaluation requires Product approval because the discount exceeds 10%;
Finance is added if margin falls below its configured floor.

## Build

```bash
mvn clean package
```

## Run pricing service

```bash
mvn -pl pricing-service spring-boot:run
```

## Sample request

```bash
curl -X POST http://localhost:8080/api/pricing/calculate \
  -H 'Content-Type: application/json' \
  -d '{"dealId":"DEAL-1001","assetsUnderManagement":500000000,"transactionVolume":1000000,"discountPercent":12,"products":["CUSTODY","FUND_ADMIN"]}'
```

## Architecture intent

The important boundary is: **Pricing calculates; Profitability measures economics; Policy decides whether an exception
requires governance; Approval manages who can authorize it.** AI can later propose a scenario but never bypasses policy
or approval.

## Production evolution

Add Spring Data JPA repositories, Flyway, OAuth/OIDC, Kafka producers/consumers, OpenAPI, Testcontainers, Flowable
runtime dependency/DI, observability, idempotency/outbox, and enterprise secrets/configuration before production use.
