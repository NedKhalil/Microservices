package com.ned.microservices.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.ned.microservices.account.model.Account;

@NoRepositoryBean
public interface AccountRepository extends JpaRepository<Account, Integer> {
  List<Account> findAllByCustomerId(Integer customerId);
}
