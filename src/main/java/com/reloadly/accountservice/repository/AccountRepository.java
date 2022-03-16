package com.reloadly.accountservice.repository;

import com.reloadly.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> getAccountByAccountNumber(long accountNumber);
}
