package com.reloadly.accountservice.entity;

import com.github.javafaker.Faker;
import com.reloadly.accountservice.constants.enums.AccountCurrency;
import com.reloadly.accountservice.constants.enums.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
class UserTest {

    private final Faker faker = new Faker();
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().build();
    }

    @Test
    @DisplayName("User Model Test")
    void testUserUpdateDto() {
        user = User.builder()

                .firstName(faker.name().firstName())
                .lastName("Reloadly")
                .phoneNumber("678908789")
                .email(faker.phoneNumber().phoneNumber())
                .build();

        assertNotNull(user);
        assertEquals("Reloadly", user.getLastName());
        assertThat(user).isExactlyInstanceOf(User.class);
    }
}