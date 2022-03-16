package com.reloadly.accountservice.bootstrap;

import com.reloadly.accountservice.dto.request.UserRegistrationRequestDto;
import com.reloadly.accountservice.entity.User;
import com.reloadly.accountservice.repository.UserRepository;
import com.reloadly.accountservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader  {

  private final UserService userService;
  private final UserRepository userRepository;

    @PostConstruct
    @Order(1)
    private void init() {
        log.info("DataLoader init- :: ");
        Optional<User> optionalUser1 = userRepository.getUserByEmail("test1-email@gmail.com");
        Optional<User> optionalUser2 = userRepository.getUserByEmail("test2-email@gmail.com");

        if (optionalUser1.isEmpty()) {

            log.info("Loading data to the DB ----->>");
            UserRegistrationRequestDto requestDto = UserRegistrationRequestDto.builder()
                    .firstName("userfirstname")
                    .lastName("userlastname")
                    .email("test1-email@gmail.com")
                    .phoneNumber("08012345678")
                    .password("12345678")
                    .confirmPassword("12345678")
                    .build();
            userService.createUserAccount(requestDto);
        }

        if (optionalUser2.isEmpty()) {
            UserRegistrationRequestDto requestDto = UserRegistrationRequestDto.builder()
                    .firstName("user2firstname")
                    .lastName("user2lastname")
                    .email("test2-email@gmail.com")
                    .phoneNumber("08012345678")
                    .password("12345678")
                    .confirmPassword("12345678")
                    .build();
            userService.createUserAccount(requestDto);
        }
    }

}
