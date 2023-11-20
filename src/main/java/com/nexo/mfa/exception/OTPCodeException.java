package com.nexo.mfa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "401 Unauthorized")
public class OTPCodeException extends ResponseStatusException {

    public OTPCodeException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
