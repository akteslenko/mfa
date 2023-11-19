package com.nexo.mfa.service;

import com.nexo.mfa.exception.OTPCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MFAService {
    private final MailService mailService;
    private final OTPCodeGenerator otpCodeGenerator;

    public MFAService(MailService mailService, OTPCodeGenerator otpCodeGenerator) {
        this.mailService = mailService;
        this.otpCodeGenerator = otpCodeGenerator;
    }

    public void sendOTPCode(String email) {
        String code = this.otpCodeGenerator.generateCode(email);
        this.mailService.sendEmail(email, code);
    }

    public void verifyOTPCode(String email, String code) {
        String generatedCode = this.otpCodeGenerator.generateCode(email);

        if (!code.equals(generatedCode)) {
            throw new OTPCodeException(HttpStatus.UNAUTHORIZED, "Invalid otp code provided.");
        }
    }
}
