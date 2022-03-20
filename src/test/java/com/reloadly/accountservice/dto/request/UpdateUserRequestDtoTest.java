package com.reloadly.accountservice.dto.request;

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
class UpdateUserRequestDtoTest {

    private final Faker faker = new Faker();
    private UpdateUserRequestDto updateUserRequestDto;

    @BeforeEach
    void setUp() {
        updateUserRequestDto = UpdateUserRequestDto.builder().build();
    }

    @Test
    @DisplayName("Update User Dto Test")
    void testUserUpdateDto() {
        updateUserRequestDto = UpdateUserRequestDto.builder()

                .phoneNumber("080123456789")
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();

        assertNotNull(updateUserRequestDto);
        assertEquals("080123456789", updateUserRequestDto.getPhoneNumber());
        assertThat(updateUserRequestDto).isExactlyInstanceOf(UpdateUserRequestDto.class);
    }
}