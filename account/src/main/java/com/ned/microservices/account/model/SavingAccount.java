package com.ned.microservices.account.model;

import java.math.BigDecimal;

import com.ned.microservices.account.types.AccountStatus;
import com.ned.microservices.account.types.AccountType;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SAVING")
@NoArgsConstructor
public class SavingAccount extends Account {
  // private double interestRate;

  // public double getInterestRate(int age) {
  // return interestRate * age;
  // }

  // public void setInterestRate(double interestRate) {
  // this.interestRate = interestRate;
  // }

  public SavingAccount(long id, int customerId) {
    super(id, BigDecimal.ZERO, customerId, AccountStatus.ACTIVE, AccountType.SAVING);
    // this.interestRate = interestRate;
  }
}
