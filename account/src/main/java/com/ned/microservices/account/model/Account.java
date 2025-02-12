/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ned.microservices.account.model;

import java.math.BigDecimal;

import com.ned.microservices.account.types.AccountStatus;
import com.ned.microservices.account.types.AccountType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Inheritance
@DiscriminatorColumn(name = "ACCOUNT_TYPE", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
public abstract class Account {

  @Id
  private long id;
  private BigDecimal balance = BigDecimal.ZERO;
  private int customerId;
  @Enumerated(EnumType.STRING)
  private AccountStatus accountStatus = AccountStatus.ACTIVE;
  @Column(name = "ACCOUNT_TYPE", insertable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  private AccountType accountType;

}
