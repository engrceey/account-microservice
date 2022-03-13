package com.reloadly.accountservice.service.implementation;

import com.reloadly.accountservice.dto.request.UpdateUserRequestDto;
import com.reloadly.accountservice.dto.request.UserRegistrationRequestDto;
import com.reloadly.accountservice.dto.response.UserResponseDto;
import com.reloadly.accountservice.entity.Account;
import com.reloadly.accountservice.entity.User;
import com.reloadly.accountservice.exceptions.ResourceCreationException;
import com.reloadly.accountservice.repository.AccountRepository;
import com.reloadly.accountservice.repository.UserRepository;
import com.reloadly.accountservice.service.UserService;
import com.reloadly.accountservice.utils.AccountNumberUtil;
import com.reloadly.accountservice.utils.ModelMapperUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public UserResponseDto createUserAccount(UserRegistrationRequestDto userRegistrationRequestDto) {

        if (doesUserAlreadyExist(userRegistrationRequestDto.getEmail())) {
            throw new ResourceCreationException("User already exist");
        }

        User newUser = new User();
        ModelMapperUtils.map(userRegistrationRequestDto,newUser);
        userRepository.save(newUser);

        Account newAccount = new Account();
        long newAccountNumber = getNewAccountNumber();
        newAccount.setAccountNumber(newAccountNumber);
        accountRepository.save(newAccount);

        return new UserResponseDto(
                userRegistrationRequestDto.getFirstName(),
                userRegistrationRequestDto.getLastName(),
                newAccountNumber,
                userRegistrationRequestDto.getEmail(),
                userRegistrationRequestDto.getPhoneNumber()
        );
    }

    @Override
    public void updateUser(UpdateUserRequestDto updateUserDto) {



    }

    private boolean doesAccountAlreadyExit(long accountNumber) {
        return accountRepository.getAccountByAccountNumber(accountNumber).isPresent();
    }

    private boolean doesUserAlreadyExist(String email) {
        return userRepository.getUserByEmail(email).isPresent();
    }

    private long getNewAccountNumber() {
        long newAccountNumber = AccountNumberUtil.generateAccountNumber();
        while (doesAccountAlreadyExit(newAccountNumber)) newAccountNumber = AccountNumberUtil.generateAccountNumber();
        return newAccountNumber;

    }
}
