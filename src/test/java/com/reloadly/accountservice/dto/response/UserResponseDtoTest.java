package com.reloadly.accountservice.dto.response;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Profile("test")
class UserResponseDtoTest {

    private final Faker faker = new Faker();
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        userResponseDto = UserResponseDto.builder().build();
    }

    @Test
    @DisplayName("User response Dto Test")
    void testUserUpdateDto() {
        userResponseDto = UserResponseDto.builder()
                .accountNumber(1238765443)
                .email("abcd@gmail.com")
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();

        assertNotNull(userResponseDto);
        assertEquals(1238765443, userResponseDto.getAccountNumber());
        assertThat(userResponseDto).isExactlyInstanceOf(UserResponseDto.class);
    }

}