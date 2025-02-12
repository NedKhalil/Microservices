/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ned.microservices.customer.model;

import java.io.Serializable;
import java.util.List;

import com.ned.microservices.customer.annotations.CustomerId;
import com.ned.microservices.customer.types.CustomerType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer implements Serializable {

  @Id
  // Generate 7 Digit Customer ID
  @CustomerId
  private int id;
  @NotNull(message = "email cannot be empty")
  private String email;
  @NotNull(message = "name cannot be empty")
  private String name;
  @NotNull(message = "address cannot be empty")
  private String address;
  @NotNull(message = "customerType must be (`RETAIL` | `INVESTMENT` | `CORPORATE`)")
  @Enumerated(EnumType.STRING)
  private CustomerType customerType;
  @Builder.Default
  private List<Long> accountIds = List.of();
}