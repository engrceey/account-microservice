package com.reloadly.accountservice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users", uniqueConstraints= @UniqueConstraint(columnNames = "email"))
public class User extends BaseEntity {

    @Column(name = "firstname", length = 100)
    private String firstName;

    @Column(name = "lastname", length = 100)
    private String lastName;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "is_verified")
    @Builder.Default
    private Boolean isVerified = false;

    @Column(name = "phone_number", length = 16)
    private String phoneNumber;

    @Column(name = "locked")
    private Boolean isLocked = false;

}