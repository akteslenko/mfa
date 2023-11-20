package com.nexo.mfa.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;


@Getter
public class SendCodeRequest {

    @NotEmpty(message = "Email could not be null or empty.")
    @Email(message = "Email address is not valid.")
    private String email;
}
