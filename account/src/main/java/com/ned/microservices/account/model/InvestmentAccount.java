package com.ned.microservices.account.model;

import java.math.BigDecimal;

import com.ned.microservices.account.types.AccountStatus;
import com.ned.microservices.account.types.AccountType;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("INVESTMENT")
@NoArgsConstructor
public class InvestmentAccount extends Account {
  // private double interestRate;

  // public double getInterestRate() {
  // return interestRate;
  // }

  // public void setInterestRate(double interestRate) {
  // this.interestRate = interestRate;
  // }

  // @Override
  // public String toString() {
  // return "InvestmentAccount{" +
  // "interestRate=" + interestRate +
  // '}';
  // }

  public InvestmentAccount(long id, int customerId) {
    super(id, BigDecimal.ZERO, customerId, AccountStatus.ACTIVE, AccountType.INVESTMENT);
    // this.interestRate = interestRate;
  }
}
