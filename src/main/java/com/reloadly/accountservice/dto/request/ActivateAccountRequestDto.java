package com.reloadly.accountservice.dto.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ActivateAccountRequestDto {
    @Size(message = "Pin should be four digits", min = 4, max = 4)
    private String pin;

}
