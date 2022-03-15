package com.reloadly.accountservice.service.implementation;

import com.reloadly.accountservice.dto.response.FetchAccountResponseDto;
import com.reloadly.accountservice.entity.Account;
import com.reloadly.accountservice.exceptions.ResourceNotFoundException;
import com.reloadly.accountservice.repository.AccountRepository;
import com.reloadly.accountservice.service.AccountService;
import com.reloadly.accountservice.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public FetchAccountResponseDto fetchAccount(long accountNumber) {

        Account account = accountRepository.getAccountByAccountNumber(accountNumber)
                .orElseThrow(
                        () -> {throw new ResourceNotFoundException("account not found");
                        }
                );
        return ModelMapperUtils.map(account,new FetchAccountResponseDto());
    }



    private boolean validateBalance(long receiverAccountNumber, BigDecimal amount) {
        Account account = accountRepository.getAccountByAccountNumber(receiverAccountNumber)
                .orElseThrow(
                        () -> {throw new ResourceNotFoundException("account number not found");
                        }
                );
        return account.getAccountBalance().compareTo(amount) >= 0;
    }
}
