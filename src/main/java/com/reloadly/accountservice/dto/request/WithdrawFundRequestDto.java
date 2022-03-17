package com.reloadly.accountservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawFundRequestDto {
    private BigDecimal amount;
}
