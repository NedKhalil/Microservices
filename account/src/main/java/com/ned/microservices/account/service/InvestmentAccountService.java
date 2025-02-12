package com.ned.microservices.account.service;

import org.springframework.stereotype.Service;

import com.ned.microservices.account.model.Account;
import com.ned.microservices.account.repository.InvestmentAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentAccountService {
  private final InvestmentAccountRepository repository;

  public void SaveAccount(Account account) {
    repository.save(account);
  }

  // public List<Account> findAllAccounts() {
  // return repository.findAll();
  // }

  // public List<Account> findAllAccountsByCustomer(Integer customerId) {
  // return repository.findAllByCustomerId(customerId);
  // }
}
