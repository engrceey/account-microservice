package com.reloadly.accountservice.dto.response;

import com.github.javafaker.Faker;
import com.reloadly.accountservice.dto.request.UpdateUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
class DepositResponseDtoTest {

    private final Faker faker = new Faker();
    private DepositResponseDto depositResponseDto;

    @BeforeEach
    void setUp() {
        depositResponseDto = DepositResponseDto.builder().build();
    }

    @Test
    @DisplayName("Deposit fund Dto Test")
    void testUserUpdateDto() {
        depositResponseDto = DepositResponseDto.builder()

                .receiverName(faker.name().firstName())
                .statusCode("00")
                .isTransactionSuccessful(false)
                .depositAccountNumber(234567890)
                .build();

        assertNotNull(depositResponseDto);
        assertEquals(234567890, depositResponseDto.getDepositAccountNumber());
        assertThat(depositResponseDto).isExactlyInstanceOf(DepositResponseDto.class);
    }
}