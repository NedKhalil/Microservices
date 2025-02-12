package com.ned.microservices.customer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ned.microservices.customer.dto.FullCustomerResponse;
import com.ned.microservices.customer.model.Customer;
import com.ned.microservices.customer.service.CustomerService;

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
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerController {
  @Autowired
  private CustomerService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Customer> save(@Valid @RequestBody Customer customer) {
    service.saveCustomer(customer);
    return ResponseEntity.ok(customer);
  }

  @GetMapping
  public ResponseEntity<List<Customer>> findAll() {
    return ResponseEntity.ok(service.findAllCustomers());
  }

  @GetMapping("/{customer-id}")
  public ResponseEntity<Customer> findCustomer(@PathVariable("customer-id") Integer customerId) {
    log.info("FindCustomer Request reached - Attempting to find customer: {}", customerId);
    Customer customer = service.findCustomerById(customerId);
    log.info("Customer found: {}", customer);
    return ResponseEntity.ok(customer);
  }

  @GetMapping("/with-accounts/{customer-id}")
  public ResponseEntity<FullCustomerResponse> findCustomerWithAccounts(
      @PathVariable("customer-id") Integer customerId) {
    log.info("FindCustomerWithAccounts Request reached - Attempting to find customer with accounts: {}", customerId);
    return ResponseEntity.ok(service.findCustomersWithAccounts(customerId));
  }

}
