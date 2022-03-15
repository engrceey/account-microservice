package com.reloadly.accountservice.controller;

import com.reloadly.accountservice.dto.request.UpdateUserRequestDto;
import com.reloadly.accountservice.dto.request.UserRegistrationRequestDto;
import com.reloadly.accountservice.dto.response.ApiResponse;
import com.reloadly.accountservice.dto.response.UserResponseDto;
import com.reloadly.accountservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping(path="/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(@RequestBody @Valid final UserRegistrationRequestDto registrationRequestDto) {
        log.info("controller register: register user :: [{}] ::", registrationRequestDto.getEmail());
        UserResponseDto response = userService.createUserAccount(registrationRequestDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        return ResponseEntity.created(uri).body(ApiResponse.<UserResponseDto>builder()
                .statusMessage("success")
                .isSuccessful(true)
                .data(response)
                .build()
        );
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ApiResponse<Boolean>> updateUser(@RequestBody final UpdateUserRequestDto updateUserRequestDto,
                                                           @PathVariable("id") final String id) {
        log.info("controller update - updated user with id :: [{}]",id);

        userService.updateUser(updateUserRequestDto, id);
        return ResponseEntity.ok(ApiResponse.<Boolean>builder()
                .isSuccessful(true)
                .statusMessage("user updated successfully")
                .data(true)
                .build()
        );
    }
}
