package com.nexo.mfa.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class VerifyCodeRequest {
    @NotEmpty(message = "Email could not be null or empty.")
    @Email(message = "Email address is not valid.")
    private String email;

    @NotEmpty(message = "Email could not be null or empty.")
    @Length(min = 6, max = 6, message = "Invalid code length.")
    private String code;
}
