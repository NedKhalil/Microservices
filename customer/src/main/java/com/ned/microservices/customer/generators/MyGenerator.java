package com.ned.microservices.customer.generators;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class MyGenerator implements IdentifierGenerator {
  @Override
  public Serializable generate(SharedSessionContractImplementor SCI, Object obj) throws HibernateException {
    Random rand = new Random();
    int num = rand.nextInt(9000000) + 1000000;
    return num;
  }
}
