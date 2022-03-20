package com.reloadly.accountservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositResponseDto {
    private String statusCode;
    private long depositAccountNumber;
    private String receiverName;
    private boolean isTransactionSuccessful;
}