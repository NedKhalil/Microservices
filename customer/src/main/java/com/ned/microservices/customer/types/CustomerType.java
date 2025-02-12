package com.ned.microservices.customer.types;

public enum CustomerType {
  RETAIL("RETAIL", "Retail"),
  CORPORATE("CORPORATE", "Corporate"),
  INVESTMENT("INVESTMENT", "Investment");

  private final String type;
  private final String name;

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  CustomerType(String type, String name) {
    this.type = type;
    this.name = name;
  }
}
