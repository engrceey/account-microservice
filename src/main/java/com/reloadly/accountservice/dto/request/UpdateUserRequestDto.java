package com.reloadly.accountservice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class UpdateUserRequestDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
