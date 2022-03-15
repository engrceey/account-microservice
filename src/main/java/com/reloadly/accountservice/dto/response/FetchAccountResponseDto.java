package com.reloadly.accountservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reloadly.accountservice.constants.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class FetchAccountResponseDto {
    private long accountNumber;
    private AccountType accountType;
    private BigDecimal accountBalance;
    private boolean isActivated;
}
