package com.ned.microservices.account.dto;

import com.ned.microservices.account.model.Account;

public record AccountResponse(long id, double balance, int customerId, String accountStatus, String accountType) {
  public AccountResponse(Account account) {
    this(account.getId(), account.getBalance().doubleValue(), account.getCustomerId(),
        account.getAccountStatus().getName(), account.getAccountType().getName());
  }
}
