package com.ned.microservices.customer.dto;

import java.util.List;

import com.ned.microservices.customer.client.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullCustomerResponse {

  private String email;
  List<Account> accounts;
}
