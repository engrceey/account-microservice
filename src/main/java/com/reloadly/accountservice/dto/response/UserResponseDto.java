package com.reloadly.accountservice.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private long accountNumber;
    private String email;
    private String phoneNumber;
}
