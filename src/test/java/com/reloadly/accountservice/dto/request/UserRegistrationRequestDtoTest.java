package com.reloadly.accountservice.dto.request;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
class UserRegistrationRequestDtoTest {

    private final Faker faker = new Faker();
    private UserRegistrationRequestDto userRegistrationRequestDto;

    @BeforeEach
    void setUp() {
        userRegistrationRequestDto = UserRegistrationRequestDto.builder().build();
    }

    @Test
    @DisplayName("Registration Dto Test")
    void testUserRegistrationDto() {
        userRegistrationRequestDto = UserRegistrationRequestDto.builder()
                .password("1234567")
                .confirmPassword("1234567")
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();

        assertNotNull(userRegistrationRequestDto);
        assertEquals("1234567", userRegistrationRequestDto.getConfirmPassword());
        assertThat(userRegistrationRequestDto).isExactlyInstanceOf(UserRegistrationRequestDto.class);
    }

}