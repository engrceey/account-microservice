package com.reloadly.accountservice.service.implementation;

import com.reloadly.accountservice.dto.request.ActivateAccountRequestDto;
import com.reloadly.accountservice.dto.response.FetchAccountResponseDto;
import com.reloadly.accountservice.entity.Account;
import com.reloadly.accountservice.entity.User;
import com.reloadly.accountservice.exceptions.ResourceNotFoundException;
import com.reloadly.accountservice.repository.AccountRepository;
import com.reloadly.accountservice.repository.UserRepository;
import com.reloadly.accountservice.service.AccountService;
import com.reloadly.accountservice.utils.AppUtil;
import com.reloadly.accountservice.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public FetchAccountResponseDto fetchAccount(long accountNumber) {

        Account account = accountRepository.getAccountByAccountNumber(accountNumber)
                .orElseThrow(
                        () -> {throw new ResourceNotFoundException("account not found");
                        }
                );
        return ModelMapperUtils.map(account,new FetchAccountResponseDto());
    }

    @Override
    public boolean activateAccount(ActivateAccountRequestDto activateAccountRequestDto) {
        Account loggedInUser = getLoggedInUserAccountDetails();
        loggedInUser.setActivated(true);
        loggedInUser.setAccountPin(passwordEncoder.encode(activateAccountRequestDto.getPin()));
        accountRepository.save(loggedInUser);
        return true;
    }


    private boolean validateBalance(long receiverAccountNumber, BigDecimal amount) {
        Account account = accountRepository.getAccountByAccountNumber(receiverAccountNumber)
                .orElseThrow(
                        () -> {throw new ResourceNotFoundException("account number not found");
                        }
                );
        return account.getAccountBalance().compareTo(amount) >= 0;
    }

    public Account getLoggedInUserAccountDetails() {
        log.info("AccountServiceImpl getLoggedInUserAccountDetails- :: ");
        String loggedInUser = AppUtil.getPrincipal();
        log.info("AccountServiceImpl getLoggedInUserAccountDetails- logged In user :: [{}]", loggedInUser);
        User user =  userRepository.getUserByEmail(loggedInUser).orElseThrow(
                () -> {throw new ResourceNotFoundException("user not found");
                }
        );
        return accountRepository.findById(user.getId()).orElseThrow(
                () -> {throw new ResourceNotFoundException("account not found");
                }
        );
    }
}
