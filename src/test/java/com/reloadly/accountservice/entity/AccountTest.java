package com.reloadly.accountservice.entity;

import com.github.javafaker.Faker;
import com.reloadly.accountservice.constants.enums.AccountCurrency;
import com.reloadly.accountservice.constants.enums.AccountType;
import com.reloadly.accountservice.dto.request.UpdateUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
class AccountTest {

    private final Faker faker = new Faker();
    private Account account;

    @BeforeEach
    void setUp() {
        account = Account.builder().build();
    }

    @Test
    @DisplayName("Account Model Test")
    void testUserUpdateDto() {
        account = Account.builder()

                .accountBalance(BigDecimal.valueOf(543.09))
                .accountNumber(1256789000)
                .accountPin("1234")
                .accountType(AccountType.SAVINGS)
                .accountCurrency(AccountCurrency.NGN)
                .build();

        assertNotNull(account);
        assertEquals(1256789000, account.getAccountNumber());
        assertThat(account).isExactlyInstanceOf(Account.class);
    }
}