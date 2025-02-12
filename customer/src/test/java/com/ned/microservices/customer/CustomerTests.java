package com.ned.microservices.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ned.microservices.customer.model.Customer;

public class CustomerTests {

  @Test
  void testSetEmail() {
    Customer customer = new Customer();
    customer.setEmail("test@example.com");
    assertEquals(customer.getEmail(), "test@example.com");
  }

  @Test
  void testGetEmail() {
    Customer customer = new Customer();
    customer.setEmail("test@example.com");
    assertEquals(customer.getEmail(), "test@example.com");
  }

}
