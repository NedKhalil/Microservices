/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ned.microservices.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ned.microservices.account.client.Customer;
import com.ned.microservices.account.client.CustomerClient;
import com.ned.microservices.account.dto.AccountRequest;
import com.ned.microservices.account.dto.AccountResponse;
import com.ned.microservices.account.event.AccountCreatedEvent;
import com.ned.microservices.account.model.Account;
import com.ned.microservices.account.model.InvestmentAccount;
import com.ned.microservices.account.model.SalaryAccount;
import com.ned.microservices.account.model.SavingAccount;
import com.ned.microservices.account.repository.AccountRepository;
import com.ned.microservices.account.types.AccountType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
  private final AccountRepository repository;
  private final InvestmentAccountService savingInvestmentservice;
  private final SavingAccountService savingAccountService;
  private final SalaryAccountService salaryAccountService;
  private final KafkaTemplate<String, AccountCreatedEvent> kafkaTemplate;
  private final CustomerClient client;

  String randomThreeDigits() {
    var val = (int) Math.floor(Math.random() * 1000);
    String digits = "000" + val;
    return digits.substring((val + "").length());
  };

  public Account saveAccount(AccountRequest account) {

    // Check if customer exists
    Customer customer = client.findCustomerById(account.customerId());
    // Check if customer has 10 accounts
    if (customer.getAccountIds().size() >= 10) {
      throw new IllegalArgumentException("Customer already has 10 accounts");
    }
    String digits = randomThreeDigits();
    long accountId = Long.parseLong(customer.getId() + "" + digits);
    AccountType accountType = account.accountType();
    Account newAccount;
    switch (accountType) {
      case SAVING -> {
        newAccount = new SavingAccount(accountId, customer.getId());
        savingAccountService.SaveAccount(newAccount);
      }
      case SALARY -> {
        newAccount = new SalaryAccount(accountId, customer.getId());
        salaryAccountService.SaveAccount(newAccount);
      }
      case INVESTMENT -> {
        newAccount = new InvestmentAccount(accountId, customer.getId());
        savingInvestmentservice.SaveAccount(newAccount);
      }
      default -> {
        throw new IllegalArgumentException("Invalid Account Type: " + accountType);
      }
    }
    // Send Event to kafka
    AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(newAccount.getCustomerId(), accountId);
    log.info("Begin - Sending Account Created Event: {}", accountCreatedEvent);
    kafkaTemplate.send("account-created", accountCreatedEvent);
    log.info("End - Sent Account Created Event: {}", accountCreatedEvent);
    return newAccount;
  }

  public List<Account> findAllAccounts() {
    return repository.findAll();
  }

  public List<AccountResponse> findAllAccountsByCustomer(Integer customerId) {
    List<Account> accounts = repository.findAllByCustomerId(customerId);
    List<AccountResponse> accsResp = accounts.stream().map(AccountResponse::new).collect(Collectors.toList());
    log.info("Found accounts: {}", accsResp);
    return accsResp;
  }
}
