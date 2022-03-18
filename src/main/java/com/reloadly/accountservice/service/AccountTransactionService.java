package com.reloadly.accountservice.service;

import com.reloadly.accountservice.dto.request.DepositAccountRequestDto;
import com.reloadly.accountservice.dto.request.TransferFundRequestDto;
import com.reloadly.accountservice.dto.request.WithdrawFundRequestDto;
import com.reloadly.accountservice.dto.response.DepositResponseDto;
import com.reloadly.accountservice.dto.response.TransferResponseDto;
import com.reloadly.accountservice.dto.response.WithdrawFundResponseDto;

public interface AccountTransactionService {
    DepositResponseDto depositFunds(DepositAccountRequestDto depositAccountRequestDto);
    TransferResponseDto transferFunds(TransferFundRequestDto transferFundRequestDto);
    WithdrawFundResponseDto withdrawFunds(WithdrawFundRequestDto withdrawFundRequestDto);
}
