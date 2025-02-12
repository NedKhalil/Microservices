package com.ned.microservices.account.types;

public enum AccountStatus {
  ACTIVE("ACTIVE", "Active"),
  INACTIVE("INACTIVE", "Inactive");

  private final String type;
  private final String name;

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  AccountStatus(String type, String name) {
    this.type = type;
    this.name = name;
  }
}
