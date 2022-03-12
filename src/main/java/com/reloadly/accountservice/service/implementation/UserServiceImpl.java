package com.reloadly.accountservice.service.implementation;

import com.reloadly.accountservice.dto.request.UserRegistrationRequestDto;
import com.reloadly.accountservice.dto.response.UserResponseDto;
import com.reloadly.accountservice.repository.AccountRepository;
import com.reloadly.accountservice.repository.UserRepository;
import com.reloadly.accountservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;

    @Override
    public UserResponseDto createUserAccount(UserRegistrationRequestDto userRegistrationRequestDto) {



        return null;
    }
}
