package com.reloadly.accountservice.controller;


import com.reloadly.accountservice.dto.response.ApiResponse;
import com.reloadly.accountservice.dto.response.FetchAccountResponseDto;
import com.reloadly.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping(path = {"accountNumber"})
    public ResponseEntity<ApiResponse<FetchAccountResponseDto>> getAccount(@PathVariable("accountNumber") final long accountNumber) {
        log.info("controller getAccount- fetch account of :: [{}]",accountNumber );

        FetchAccountResponseDto response = accountService.fetchAccount(accountNumber);
        return ResponseEntity.ok(ApiResponse.<FetchAccountResponseDto>builder()
                .isSuccessful(true)
                .statusMessage("account details")
                .data(response)
                .build()
        );

    }
}
