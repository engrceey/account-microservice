package com.reloadly.accountservice.service;

import com.reloadly.accountservice.dto.request.DepositAccountRequestDto;
import com.reloadly.accountservice.dto.request.TransferFundRequestDto;
import com.reloadly.accountservice.dto.request.WithdrawFundRequestDto;
import com.reloadly.accountservice.dto.response.TransferResponseDto;

public interface AccountTransactionService {
    boolean depositFunds(DepositAccountRequestDto depositAccountRequestDto);
    TransferResponseDto transferFunds(TransferFundRequestDto transferFundRequestDto);
    boolean withdrawFunds(WithdrawFundRequestDto withdrawFundRequestDto);
}
