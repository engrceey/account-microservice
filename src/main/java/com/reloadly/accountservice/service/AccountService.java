package com.reloadly.accountservice.service;

import com.reloadly.accountservice.dto.request.ActivateAccountRequestDto;
import com.reloadly.accountservice.dto.response.FetchAccountResponseDto;

public interface AccountService {
    FetchAccountResponseDto fetchAccount(long accountNumber);
    boolean activateAccount(ActivateAccountRequestDto activateAccountRequestDto);

}
