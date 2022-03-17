package com.reloadly.accountservice.service;

import com.reloadly.accountservice.dto.request.DepositAccountRequestDto;
import com.reloadly.accountservice.dto.request.TransferFundRequestDto;
import com.reloadly.accountservice.dto.request.WithdrawFundRequestDto;

public interface AccountTransactionService {
    boolean depositFunds(DepositAccountRequestDto depositAccountRequestDto);
    boolean transferFunds(TransferFundRequestDto transferFundRequestDto);
    boolean withdrawFunds(WithdrawFundRequestDto withdrawFundRequestDto);
}
