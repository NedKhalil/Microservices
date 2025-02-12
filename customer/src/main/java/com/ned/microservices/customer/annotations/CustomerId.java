package com.ned.microservices.customer.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import org.hibernate.annotations.IdGeneratorType;

import com.ned.microservices.customer.generators.MyGenerator;

@IdGeneratorType(MyGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD })
public @interface CustomerId {
}
