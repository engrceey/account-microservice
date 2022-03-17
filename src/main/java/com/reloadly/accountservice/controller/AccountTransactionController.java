package com.reloadly.accountservice.controller;


import com.reloadly.accountservice.dto.request.ActivateAccountRequestDto;
import com.reloadly.accountservice.dto.request.DepositAccountRequestDto;
import com.reloadly.accountservice.dto.response.ApiResponse;
import com.reloadly.accountservice.service.AccountTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @PostMapping("/deposit-fund")
    public ResponseEntity<ApiResponse<Boolean>> depositFunds(@RequestBody @Valid final DepositAccountRequestDto depositAccountRequestDto) {
        log.info("controller depositFunds- for :: [{}]", depositAccountRequestDto.getReceiverAccountNumber() );

        boolean response = accountTransactionService.depositFunds(depositAccountRequestDto);

        return ResponseEntity.ok(ApiResponse.<Boolean>builder()
                .isSuccessful(true)
                .statusMessage("fund deposited successfully")
                .data(response)
                .build()
        );

    }

}
