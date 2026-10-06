CREATE TABLE client
(
    client_id   VARCHAR2(36) PRIMARY KEY,
    client_name VARCHAR2(200),
    segment     VARCHAR2(80),
    region      VARCHAR2(80)
);
CREATE TABLE deal
(
    deal_id    VARCHAR2(36) PRIMARY KEY,
    client_id  VARCHAR2(36),
    status     VARCHAR2(40),
    version_no NUMBER,
    currency   VARCHAR2(3),
    CONSTRAINT fk_deal_client FOREIGN KEY (client_id) REFERENCES client (client_id)
);
CREATE TABLE pricing_model
(
    model_id        VARCHAR2(36) PRIMARY KEY,
    product_id      VARCHAR2(36),
    version_no      NUMBER,
    effective_date  DATE,
    expiration_date DATE,
    status          VARCHAR2(30)
);
CREATE TABLE pricing_rule
(
    rule_id   VARCHAR2(36) PRIMARY KEY,
    model_id  VARCHAR2(36),
    fee_type  VARCHAR2(40),
    tier_code VARCHAR2(40),
    rate      NUMBER(18,8),
    min_value NUMBER(18,2),
    max_value NUMBER(18,2)
);
CREATE TABLE pricing_result
(
    result_id     VARCHAR2(36) PRIMARY KEY,
    deal_id       VARCHAR2(36),
    scenario_id   VARCHAR2(36),
    revenue       NUMBER(18,2),
    cost          NUMBER(18,2),
    profit        NUMBER(18,2),
    margin        NUMBER(9,4),
    calculated_at TIMESTAMP
);
CREATE TABLE approval
(
    approval_id   VARCHAR2(36) PRIMARY KEY,
    deal_id       VARCHAR2(36),
    approver_role VARCHAR2(40),
    status        VARCHAR2(30),
    comments      VARCHAR2(1000),
    decision_ts   TIMESTAMP
);
CREATE TABLE audit_event
(
    event_id       VARCHAR2(36) PRIMARY KEY,
    entity_type    VARCHAR2(60),
    entity_id      VARCHAR2(36),
    action         VARCHAR2(60),
    user_id        VARCHAR2(100),
    event_ts       TIMESTAMP,
    correlation_id VARCHAR2(80)
);
