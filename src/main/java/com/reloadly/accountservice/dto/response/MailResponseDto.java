package com.reloadly.accountservice.dto.response;

import lombok.Data;

@Data
public class MailResponseDto {
        private String statusMessage;
        private boolean isSuccessful;
        private boolean data;
}
