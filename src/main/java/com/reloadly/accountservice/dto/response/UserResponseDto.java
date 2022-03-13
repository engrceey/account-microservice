package com.reloadly.accountservice.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private long accountNumber;
    private String email;
    private String phoneNumber;
}
