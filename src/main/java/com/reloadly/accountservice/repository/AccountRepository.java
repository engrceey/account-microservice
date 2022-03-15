package com.reloadly.accountservice.repository;

import com.reloadly.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> getAccountByAccountNumber(long accountNumber);
}
