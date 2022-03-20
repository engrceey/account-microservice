package com.reloadly.accountservice.service;

import com.github.javafaker.Faker;
import com.reloadly.accountservice.controller.AccountController;
import com.reloadly.accountservice.controller.AccountTransactionController;
import com.reloadly.accountservice.dto.request.DepositAccountRequestDto;
import com.reloadly.accountservice.dto.response.ApiResponse;
import com.reloadly.accountservice.dto.response.DepositResponseDto;
import com.reloadly.accountservice.dto.response.FetchAccountResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class AccountTransactionServiceTest {

    private final Faker faker = new Faker();
    @Mock
    private AccountTransactionService accountTransactionService;
    @InjectMocks
    private AccountTransactionController accountTransactionController;

    private DepositAccountRequestDto depositAccountRequestDto;
    private DepositResponseDto depositResponseDto;

    @BeforeEach
    public void setUp() {
        depositAccountRequestDto = DepositAccountRequestDto.builder()
                .receiverAccountNumber(7585858548L)
                .amount(BigDecimal.valueOf(7899.00))
                .receiver("Reloadly")
                .sender(faker.name().lastName())
                .build();
        DepositResponseDto responseDto = accountTransactionService.depositFunds(depositAccountRequestDto);
        lenient().when(depositResponseDto).thenReturn(depositResponseDto);
         depositResponseDto = responseDto;
    }

    @Test
    @DisplayName("Controller Deposit-Fund Endpoint ")
    void testFetchAccount() {
        ResponseEntity<ApiResponse<DepositResponseDto>> response = accountTransactionController.depositFunds(depositAccountRequestDto);
        assertThat(Objects.requireNonNull(response.getBody()).getIsSuccessful()).isEqualTo(true);
    }

}