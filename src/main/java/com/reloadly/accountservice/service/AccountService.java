package com.reloadly.accountservice.service;

import com.reloadly.accountservice.dto.request.ActivateAccountRequestDto;
import com.reloadly.accountservice.dto.response.FetchAccountResponseDto;
import com.reloadly.accountservice.entity.Account;

public interface AccountService {
    FetchAccountResponseDto fetchAccount(long accountNumber);
    boolean activateAccount(ActivateAccountRequestDto activateAccountRequestDto);
    Account getLoggedInUserAccountDetails();

}
