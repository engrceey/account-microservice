package com.reloadly.accountservice.controller;


import com.reloadly.accountservice.dto.request.ActivateAccountRequestDto;
import com.reloadly.accountservice.dto.response.ApiResponse;
import com.reloadly.accountservice.dto.response.FetchAccountResponseDto;
import com.reloadly.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/")
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = "{accountNumber}")
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

    @PostMapping("/pin")
    public ResponseEntity<ApiResponse<Boolean>> setAccountPin(@RequestBody final ActivateAccountRequestDto activateAccountRequestDto) {
        log.info("controller setAccountPin- account of ::" );

        Boolean response = accountService.activateAccount(activateAccountRequestDto);
        return ResponseEntity.ok(ApiResponse.<Boolean>builder()
                .isSuccessful(true)
                .statusMessage("account pin set")
                .data(response)
                .build()
        );

    }
}
