package com.reloadly.accountservice.service.implementation;

import com.reloadly.accountservice.dto.request.DepositAccountRequestDto;
import com.reloadly.accountservice.dto.request.TransferFundRequestDto;
import com.reloadly.accountservice.dto.request.WithdrawFundRequestDto;
import com.reloadly.accountservice.dto.response.FetchAccountResponseDto;
import com.reloadly.accountservice.entity.Account;
import com.reloadly.accountservice.exceptions.InsufficientBalanceException;
import com.reloadly.accountservice.exceptions.ResourceNotFoundException;
import com.reloadly.accountservice.repository.AccountRepository;
import com.reloadly.accountservice.service.AccountService;
import com.reloadly.accountservice.service.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public boolean depositFunds(DepositAccountRequestDto depositAccountRequestDto) {
        Account account = getAccount(depositAccountRequestDto.getReceiverAccountNumber());
        BigDecimal newBalance = account.getAccountBalance().add(depositAccountRequestDto.getAmount());

        log.info("Funding account of :: [{}] :: with :: [{}] ::", account.getAccountNumber(), newBalance);
        account.setAccountBalance(newBalance);
        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean transferFunds(TransferFundRequestDto transferFundRequestDto) {

        Account transferAccount = getAccountLogIn();
        Account receiverAccount = getAccount(transferFundRequestDto.getReceiverAccountNumber());

        if (isAccountEligibleForTransfer(transferAccount.getAccountNumber(), transferFundRequestDto.getAmount())) {
            BigDecimal newTransfererBalance = transferAccount.getAccountBalance().subtract(transferFundRequestDto.getAmount());
            BigDecimal newReceiverBalance = receiverAccount.getAccountBalance().add(transferFundRequestDto.getAmount());

            transferAccount.setAccountBalance(newTransfererBalance);
            accountRepository.save(transferAccount);

            receiverAccount.setAccountBalance(newReceiverBalance);
            accountRepository.save(receiverAccount);

            return true;

        }
        throw new InsufficientBalanceException("Insufficient balance to complete this transaction", HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean withdrawFunds(WithdrawFundRequestDto withdrawFundRequestDto) {

        Account withdrawAccount = getAccountLogIn();

        if(isAccountEligibleForTransfer(withdrawAccount.getAccountNumber(), withdrawFundRequestDto.getAmount())) {
            BigDecimal newWithdrawAmount = withdrawAccount.getAccountBalance().subtract(withdrawFundRequestDto.getAmount());
            withdrawAccount.setAccountBalance(newWithdrawAmount);
            accountRepository.save(withdrawAccount);

            return true;
        }
        throw new InsufficientBalanceException("Insufficient balance to complete this transaction", HttpStatus.BAD_REQUEST);
    }


    private boolean isAccountEligibleForTransfer(long account, BigDecimal amount) {
        FetchAccountResponseDto accountDetails = accountService.fetchAccount(account);

        return accountDetails.getAccountBalance().compareTo(amount) >= 0;
    }

    private Account getAccount(long accountNumber) {
        return accountRepository.getAccountByAccountNumber(accountNumber).orElseThrow(
                () -> {throw new ResourceNotFoundException("account not found");
                }
        );
    }

    private Account getAccountLogIn(){
       return accountRepository.
                getAccountByAccountNumber(accountService.getLoggedInUserAccountDetails().getAccountNumber()).get();
    }


}
