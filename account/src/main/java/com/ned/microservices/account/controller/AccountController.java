package com.ned.microservices.account.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ned.microservices.account.dto.AccountRequest;
import com.ned.microservices.account.dto.AccountResponse;
import com.ned.microservices.account.model.Account;
import com.ned.microservices.account.service.AccountService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
public class AccountController {
  @Autowired
  private AccountService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Account> save(@Valid @RequestBody AccountRequest account) {
    log.info("SaveAccount Request reached - Attempting to save account: {}", account);
    Account newAccount = service.saveAccount(account);
    log.info("Account saved: {}", newAccount);
    return ResponseEntity.ok(newAccount);
  }

  @GetMapping
  public ResponseEntity<List<Account>> findAll() {
    log.info("FindAll Request reached - Attempting to find all accounts");
    return ResponseEntity.ok(service.findAllAccounts());
  }

  @GetMapping("/customer/{customer-id}")
  public ResponseEntity<List<AccountResponse>> findAllByCustomer(@PathVariable("customer-id") Integer customerId) {
    log.info("FindAllByCustomer Request reached - Attempting to find all accounts for customer: {}", customerId);
    return ResponseEntity.ok(service.findAllAccountsByCustomer(customerId));
  }

}
