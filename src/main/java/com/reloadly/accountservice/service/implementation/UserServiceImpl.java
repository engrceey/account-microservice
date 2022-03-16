package com.reloadly.accountservice.service.implementation;

import com.reloadly.accountservice.dto.request.EmailRequestDto;
import com.reloadly.accountservice.dto.request.UpdateUserRequestDto;
import com.reloadly.accountservice.dto.request.UserRegistrationRequestDto;
import com.reloadly.accountservice.dto.response.UserResponseDto;
import com.reloadly.accountservice.entity.Account;
import com.reloadly.accountservice.entity.User;
import com.reloadly.accountservice.entity.VerificationToken;
import com.reloadly.accountservice.exceptions.CustomException;
import com.reloadly.accountservice.exceptions.ResourceCreationException;
import com.reloadly.accountservice.exceptions.ResourceNotFoundException;
import com.reloadly.accountservice.repository.AccountRepository;
import com.reloadly.accountservice.repository.UserRepository;
import com.reloadly.accountservice.repository.VerificationTokenRepository;
import com.reloadly.accountservice.service.MailSenderService;
import com.reloadly.accountservice.service.UserService;
import com.reloadly.accountservice.utils.AccountNumberUtil;
import com.reloadly.accountservice.utils.ModelMapperUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailSenderService mailSenderService;

    @Override
    public UserResponseDto createUserAccount(final UserRegistrationRequestDto userRegistrationRequestDto) {

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

        String token = generateVerificationToken(newUser);

        log.info("Sending email to {}", userRegistrationRequestDto.getEmail());

        try {
            mailSenderService.sendMail(EmailRequestDto
                    .builder()
                    .sender("noreply@gmail.com")
                    .subject("Please Activate Your Account")
                    .body("Thank you for Creating your account with us " +
                            "please click on the link below to activate your account : " +
                            "http://localhost:9091/api/v1/auth/verify-account/" + token)
                    .recipient(newUser.getEmail())
                    .build());
        } catch (Exception exp) {
            log.error("Exception occurred msg :: [{}]", exp.getMessage());
            throw new CustomException("Exception occurred sending msg");
        }
        return new UserResponseDto(
                userRegistrationRequestDto.getFirstName(),
                userRegistrationRequestDto.getLastName(),
                newAccountNumber,
                userRegistrationRequestDto.getEmail(),
                userRegistrationRequestDto.getPhoneNumber()

        );
    }

    private String generateVerificationToken(User user){
        log.info("inside generateVerificationToken");
        log.info("inside generateVerificationToken, generating token for {}", user.getEmail());
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);

        log.info("Saving token to database");
        verificationTokenRepository.save(verificationToken);
        return token;
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

    @Override
    public String verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.isPresent()) {
            Boolean isVerified = verificationToken.get().getUser().getIsVerified();
            if(isVerified) {
                throw new CustomException("token used, please request another activation token", HttpStatus.BAD_REQUEST);
            }
            return fetchUserAndEnable(verificationToken.get());
        }
        throw new CustomException("token invalid");
    }

    @Transactional
    private String fetchUserAndEnable(VerificationToken verificationToken) {
        User user = verificationToken.getUser();

        if(user == null) {
            throw new ResourceNotFoundException("User with token not found");
        }
        user.setIsVerified(true);
        userRepository.save(user);
        return "Account verified successfully";
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
