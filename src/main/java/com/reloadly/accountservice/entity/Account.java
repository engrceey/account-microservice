package com.reloadly.accountservice.entity;

import com.reloadly.accountservice.constants.enums.AccountCurrency;
import com.reloadly.accountservice.constants.enums.AccountType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "accounts")
public class Account extends BaseEntity{

    @Column(name = "account_number", length = 10)
    private String accountNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_type", length = 10)
    @Builder.Default
    private AccountType accountType = AccountType.SAVINGS;

    @Column(name = "account_balance")
    @Builder.Default
    private BigDecimal accountBalance = BigDecimal.valueOf(0.0);

    @Column(name = "account_pin", length = 4)
    private Integer accountPin;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_currency", length = 4)
    @Builder.Default
    private AccountCurrency accountCurrency = AccountCurrency.EUR;

}