package com.ned.microservices.account.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//For communicating with a third party api
@FeignClient(name = "customer-service", url = "${application.config.customers-url}")
public interface CustomerClient {

  @GetMapping("/{customer-id}")
  Customer findCustomerById(@PathVariable("customer-id") Integer customerId);
}