package com.reloadly.accountservice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reloadly.accountservice.utils.PasswordMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@PasswordMatch(message = "validation.user.password.match")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserRegistrationRequestDto {

    @NotBlank(message = "firstName cannot be empty")
    @Size(message = "FirstName character length cannot be less than 3 and more than 100", min = 3, max = 100)
    private String firstName;

    @NotBlank(message = "Lastname cannot be empty")
    @Size(message = "Lastname character length cannot be less than 3 and more than 100", min = 3, max = 100)
    private String lastName;

    @NotBlank(message = "email cannot be empty")
    @Email(message = "Must be a valid email!")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(message = "Password must be greater than 6 and less than 20",min = 6, max = 20)
    private String password;

    @NotBlank(message = "Confirm password cannot be empty")
    @Size(message = "Password must be greater than 6 and less than 20",min = 6, max = 20)
    private String confirmPassword;

    @NotBlank(message = "email cannot be empty")
    @Size(message = "Phone number character length cannot be less than 11 and more than 16", min = 11, max = 16)
    private String phoneNumber;

}
