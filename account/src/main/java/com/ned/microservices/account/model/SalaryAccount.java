package com.ned.microservices.account.model;

import java.math.BigDecimal;

import com.ned.microservices.account.types.AccountStatus;
import com.ned.microservices.account.types.AccountType;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SALARY")
@NoArgsConstructor
public class SalaryAccount extends Account {
  // private double salary;

  // public double getSalary() {
  // return salary;
  // }

  // public void setSalary(double salary) {
  // this.salary = salary;
  // }

  // @Override
  // public String toString() {
  // return "SalaryAccount{" +
  // "salary=" + salary +
  // '}';
  // }

  public SalaryAccount(long id, int customerId) {
    super(id, BigDecimal.ZERO, customerId, AccountStatus.ACTIVE, AccountType.SALARY);
    // this.interestRate = interestRate;
  }
}
