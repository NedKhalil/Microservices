package com.ned.microservices.customer.client;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  private long id;
  private BigDecimal balance;
  private int customerId;
  private String accountType;
}
