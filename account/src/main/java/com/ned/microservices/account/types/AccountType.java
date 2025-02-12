package com.ned.microservices.account.types;

public enum AccountType {
  SAVING("SAVING", "Saving"),
  SALARY("SALARY", "Salary"),
  INVESTMENT("INVESTMENT", "Investment");

  private final String type;
  private final String name;

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  AccountType(String type, String name) {
    this.type = type;
    this.name = name;
  }
}
