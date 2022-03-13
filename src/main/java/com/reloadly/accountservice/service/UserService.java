package com.reloadly.accountservice.service;

import com.reloadly.accountservice.dto.request.UpdateUserRequestDto;
import com.reloadly.accountservice.dto.request.UserRegistrationRequestDto;
import com.reloadly.accountservice.dto.response.UserResponseDto;

public interface UserService {
        UserResponseDto createUserAccount(UserRegistrationRequestDto userRegistrationRequestDto);
        void updateUser(UpdateUserRequestDto updateUserDto);
}
