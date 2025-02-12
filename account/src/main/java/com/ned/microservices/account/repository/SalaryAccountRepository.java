package com.ned.microservices.account.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface SalaryAccountRepository extends AccountRepository {
}
