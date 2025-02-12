package com.ned.microservices.customer.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//For communicating with a third party api
@FeignClient(name = "account-service", url = "${application.config.accounts-url}")
public interface AccountClient {

  @GetMapping("/customer/{customer-id}")
  List<Account> findAllAccountsByCustomer(@PathVariable("customer-id") Integer customerId);
}