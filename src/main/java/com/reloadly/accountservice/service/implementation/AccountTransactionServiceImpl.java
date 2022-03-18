package com.reloadly.accountservice.service.implementation;

import com.reloadly.accountservice.constants.AppConstant;
import com.reloadly.accountservice.dto.request.DepositAccountRequestDto;
import com.reloadly.accountservice.dto.request.TransferFundRequestDto;
import com.reloadly.accountservice.dto.request.WithdrawFundRequestDto;
import com.reloadly.accountservice.dto.response.DepositResponseDto;
import com.reloadly.accountservice.dto.response.FetchAccountResponseDto;
import com.reloadly.accountservice.dto.response.TransferResponseDto;
import com.reloadly.accountservice.dto.response.WithdrawFundResponseDto;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public DepositResponseDto depositFunds(DepositAccountRequestDto depositAccountRequestDto) {
        Account account = getAccount(depositAccountRequestDto.getReceiverAccountNumber());
        BigDecimal newBalance = account.getAccountBalance().add(depositAccountRequestDto.getAmount());

        log.info("Funding account of :: [{}] :: with :: [{}] ::", account.getAccountNumber(), newBalance);
        account.setAccountBalance(newBalance);
        accountRepository.save(account);

        return DepositResponseDto
                .builder()
                .depositAccountNumber(depositAccountRequestDto.getReceiverAccountNumber())
                .isTransactionSuccessful(true)
                .receiverName(depositAccountRequestDto.getReceiver())
                .statusCode(AppConstant.Status.SUCCESSFUL.getCode())
                .build();

    }

    @Override
    public TransferResponseDto transferFunds(TransferFundRequestDto transferFundRequestDto) {
        log.info("Transaction service transferFunds");
        Account transferAccount = getAccountLogIn();
        Account receiverAccount = getAccount(transferFundRequestDto.getReceiverAccountNumber());

        if (isAccountEligibleForTransfer(transferAccount.getAccountNumber(), transferFundRequestDto.getAmount())) {
            BigDecimal newTransfererBalance = transferAccount.getAccountBalance().subtract(transferFundRequestDto.getAmount());
            BigDecimal newReceiverBalance = receiverAccount.getAccountBalance().add(transferFundRequestDto.getAmount());

            transferAccount.setAccountBalance(newTransfererBalance);
            accountRepository.save(transferAccount);

            receiverAccount.setAccountBalance(newReceiverBalance);
            accountRepository.save(receiverAccount);

            log.info("finished transfer, sending response");
            return TransferResponseDto
                    .builder()
                    .sendAccountNumber(transferAccount.getAccountNumber())
                    .isTransactionSuccessful(true)
                    .receiverName(receiverAccount.getUser().getFirstName())
                    .senderName(transferAccount.getUser().getFirstName())
                    .statusCode(AppConstant.Status.SUCCESSFUL.getCode())
                    .build();

        }
        throw new InsufficientBalanceException("Insufficient balance to complete this transaction", HttpStatus.BAD_REQUEST);
    }

    @Override
    public WithdrawFundResponseDto withdrawFunds(WithdrawFundRequestDto withdrawFundRequestDto) {
        log.info("Account Transaction service ");
        Account withdrawAccount = getAccountLogIn();

        if(isAccountEligibleForTransfer(withdrawAccount.getAccountNumber(), withdrawFundRequestDto.getAmount())) {
            BigDecimal newWithdrawAmount = withdrawAccount.getAccountBalance().subtract(withdrawFundRequestDto.getAmount());
            withdrawAccount.setAccountBalance(newWithdrawAmount);
            accountRepository.save(withdrawAccount);
            log.info("finished withdraw for :: [{}]", withdrawAccount.getAccountNumber());

            return WithdrawFundResponseDto
                    .builder()
                    .accountNum(withdrawAccount.getAccountNumber())
                    .isSuccessful(true)
                    .status(AppConstant.Status.SUCCESSFUL.getCode())
                    .name(withdrawAccount.getUser().getFirstName())
                    .build();
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
