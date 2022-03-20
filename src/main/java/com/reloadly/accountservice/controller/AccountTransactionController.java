package com.reloadly.accountservice.controller;


import com.reloadly.accountservice.dto.request.DepositAccountRequestDto;
import com.reloadly.accountservice.dto.request.TransferFundRequestDto;
import com.reloadly.accountservice.dto.request.WithdrawFundRequestDto;
import com.reloadly.accountservice.dto.response.ApiResponse;
import com.reloadly.accountservice.dto.response.DepositResponseDto;
import com.reloadly.accountservice.dto.response.TransferResponseDto;
import com.reloadly.accountservice.dto.response.WithdrawFundResponseDto;
import com.reloadly.accountservice.service.AccountTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @PostMapping("/deposit-fund")
    public ResponseEntity<ApiResponse<DepositResponseDto>> depositFunds(@RequestBody @Valid final DepositAccountRequestDto depositAccountRequestDto) {
        log.info("controller depositFunds- for :: [{}]", depositAccountRequestDto.getReceiverAccountNumber() );
        DepositResponseDto response = accountTransactionService.depositFunds(depositAccountRequestDto);
        return ResponseEntity.ok(ApiResponse.<DepositResponseDto>builder()
                .isSuccessful(true)
                .statusMessage("fund deposited successfully")
                .data(response)
                .build()
        );
    }


    @PostMapping("/withdraw-fund")
    public ResponseEntity<ApiResponse<WithdrawFundResponseDto>> withdrawFunds(@RequestBody @Valid final WithdrawFundRequestDto withdrawFundRequestDto) {
        log.info("controller withdrawFunds- for :: [{}]", withdrawFundRequestDto.getAmount() );
        WithdrawFundResponseDto response = accountTransactionService.withdrawFunds(withdrawFundRequestDto);
        return ResponseEntity.ok(ApiResponse.<WithdrawFundResponseDto>builder()
                .isSuccessful(true)
                .statusMessage("fund withdraw successfully")
                .data(response)
                .build()
        );
    }

    @PostMapping("/transfer-fund")
    public ResponseEntity<ApiResponse<TransferResponseDto>> transferFunds(@RequestBody @Valid final TransferFundRequestDto transferFundRequestDto) {
        log.info("controller transferFunds- for :: [{}]", transferFundRequestDto.getReceiverAccountNumber() );
        TransferResponseDto response = accountTransactionService.transferFunds(transferFundRequestDto);
        return ResponseEntity.ok(ApiResponse.<TransferResponseDto>builder()
                .isSuccessful(true)
                .statusMessage("fund transferred successfully")
                .data(response)
                .build()
        );
    }

}
