package com.reloadly.accountservice.dto.request;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferFundRequestDto {
    private String receiver;
    private long receiverAccountNumber;
    private BigDecimal amount;

}
