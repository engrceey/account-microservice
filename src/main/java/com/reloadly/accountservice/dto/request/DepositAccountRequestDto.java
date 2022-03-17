package com.reloadly.accountservice.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public  class DepositAccountRequestDto {

    @NotNull(message = "account cannot be blank")
    @Min(value = 0)
    private BigDecimal amount;

    @NotBlank(message = "sender cannot be blank")
    private String sender;

    @NotBlank(message = "receiver cannot be blank")
    private String receiver;

    @NotNull(message = "account number cannot be blank")
    private Long receiverAccountNumber;

}
