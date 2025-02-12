package com.ned.microservices.account.dto;

import org.hibernate.validator.constraints.Range;

import com.ned.microservices.account.types.AccountType;

import jakarta.validation.constraints.NotNull;

public record AccountRequest(
    @Range(min = 1000000, max = 9999999, message = "customerId must be between 7 digits") int customerId,
    @NotNull(message = "accountType must be (`SALARY` | `INVESTMENT` | `SAVING`)") AccountType accountType) {
}