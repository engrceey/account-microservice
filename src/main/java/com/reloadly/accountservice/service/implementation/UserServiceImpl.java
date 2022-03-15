package com.reloadly.accountservice.service.implementation;

import com.reloadly.accountservice.dto.request.UpdateUserRequestDto;
import com.reloadly.accountservice.dto.request.UserRegistrationRequestDto;
import com.reloadly.accountservice.dto.response.UserResponseDto;
import com.reloadly.accountservice.entity.Account;
import com.reloadly.accountservice.entity.User;
import com.reloadly.accountservice.exceptions.ResourceCreationException;
import com.reloadly.accountservice.exceptions.ResourceNotFoundException;
import com.reloadly.accountservice.repository.AccountRepository;
import com.reloadly.accountservice.repository.UserRepository;
import com.reloadly.accountservice.service.UserService;
import com.reloadly.accountservice.utils.AccountNumberUtil;
import com.reloadly.accountservice.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUserAccount(UserRegistrationRequestDto userRegistrationRequestDto) {

        if (doesUserAlreadyExist(userRegistrationRequestDto.getEmail())) {
            throw new ResourceCreationException("User already exist");
        }

        User newUser = new User();
        ModelMapperUtils.map(userRegistrationRequestDto,newUser);
        newUser.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        userRepository.save(newUser);

        Account newAccount = new Account();
        long newAccountNumber = getNewAccountNumber();
        newAccount.setAccountNumber(newAccountNumber);
        newAccount.setUser(newUser);
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
    public void updateUser(UpdateUserRequestDto updateUserDto, String id) {

        log.info("service updateUser - updating user with id :: [{}] ::", id);
       User user = userRepository.findById(id).<ResourceNotFoundException>orElseThrow(
                () -> {throw new ResourceNotFoundException("user does not exist");
                }
        );

       if (StringUtils.isNoneBlank(updateUserDto.getFirstName()))
           user.setFirstName(updateUserDto.getFirstName());

       if (StringUtils.isNoneBlank(updateUserDto.getLastName()))
           user.setLastName(updateUserDto.getLastName());

       if (StringUtils.isNoneBlank(updateUserDto.getPhoneNumber()))
           user.setPhoneNumber(updateUserDto.getPhoneNumber());

        userRepository.save(user);
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


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(
                        () -> {throw new ResourceNotFoundException("user does not exist");
                        }
                );

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorities);
    }
}
