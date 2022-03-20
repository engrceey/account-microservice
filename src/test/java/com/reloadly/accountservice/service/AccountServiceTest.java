package com.reloadly.accountservice.service;

import com.github.javafaker.Faker;
import com.reloadly.accountservice.controller.AccountController;
import com.reloadly.accountservice.dto.request.ActivateAccountRequestDto;
import com.reloadly.accountservice.dto.request.DepositAccountRequestDto;
import com.reloadly.accountservice.dto.response.ApiResponse;
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
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    private final Faker faker = new Faker();
    @Mock
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;
    private DepositAccountRequestDto depositAccountRequestDto;
    private FetchAccountResponseDto fetchAccountResponseDto;

    @BeforeEach
    public void setUp() {
        depositAccountRequestDto = DepositAccountRequestDto.builder()
                .receiverAccountNumber(7585858548L)
                .amount(BigDecimal.valueOf(7899.00))
                .receiver("Reloadly")
                .sender(faker.name().lastName())
                .build();
        FetchAccountResponseDto account = accountService.fetchAccount (7585858548L);
        lenient().when(account).thenReturn(account);
        fetchAccountResponseDto = account;

    }

    @Test
    @DisplayName("Controller Get-Account Endpoint ")
    void testFetchAccount() {
        ResponseEntity<ApiResponse<FetchAccountResponseDto>> response = accountController.getAccount(7585858548L);
        assertThat(Objects.requireNonNull(response.getBody()).getIsSuccessful()).isEqualTo(true);
    }

}