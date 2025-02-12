/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ned.microservices.customer.service;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ned.microservices.account.event.AccountCreatedEvent;
import com.ned.microservices.customer.client.Account;
import com.ned.microservices.customer.client.AccountClient;
import com.ned.microservices.customer.dto.FullCustomerResponse;
import com.ned.microservices.customer.exceptions.CustomerNotFoundException;
import com.ned.microservices.customer.model.Customer;
import com.ned.microservices.customer.respository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {
  private final CustomerRepository repository;
  private final AccountClient client;

  public void saveCustomer(Customer customer) {
    repository.save(customer);
  }

  public List<Customer> findAllCustomers() {
    return repository.findAll();
  }

  public Customer findCustomerById(Integer customerId) {
    return repository.findById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
  }

  public FullCustomerResponse findCustomersWithAccounts(Integer customerId) {
    Customer customer = findCustomerById(customerId);
    List<Account> accounts = client.findAllAccountsByCustomer(customerId);
    log.info("Found Customer Accounts: {}", accounts);
    return FullCustomerResponse.builder()
        .email(customer.getEmail())
        .accounts(accounts)
        .build();
  }

  @KafkaListener(topics = "account-created")
  public void saveAccountToCustomer(AccountCreatedEvent event) {
    log.info("Event message received from account service: `account-created` {}", event);
    var customer = findCustomerById(event.getCustomerId());
    customer.getAccountIds().add(event.getAccountId());
    repository.save(customer);
    log.info("Customer updated with new account: {}", customer);
  }
}
