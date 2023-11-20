package com.nexo.mfa.unit.service;

import com.nexo.mfa.exception.OTPCodeException;
import com.nexo.mfa.service.MFAService;
import com.nexo.mfa.service.MailService;
import com.nexo.mfa.service.OTPCodeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MFAServiceTest {
    @Mock
    private MailService mailService;

    @Mock
    private OTPCodeGenerator otpCodeGenerator;

    @InjectMocks
    private MFAService mfaService;

    @Test
    void sendOTPCodeGenerateCodeAndSendEmail() {
        String email = "user@example.com";
        String generatedCode = "123456";

        Mockito.when(otpCodeGenerator.generateCode(email)).thenReturn(generatedCode);

        mfaService.sendOTPCode(email);

        verify(otpCodeGenerator).generateCode(email);
        verify(mailService).sendEmail(email, generatedCode);
    }

    @Test
    void verifyOTPCodeThrowExceptionForInvalidCode() {
        String email = "user@example.com";
        String providedCode = "654321";
        String generatedCode = "123456";

        Mockito.when(otpCodeGenerator.generateCode(email)).thenReturn(generatedCode);

        assertThrows(OTPCodeException.class, () -> mfaService.verifyOTPCode(email, providedCode), "Invalid otp code provided.");
    }

    @Test
    void verifyOTPCodeNotThrowExceptionForValidCode() {
        String email = "user@example.com";
        String validCode = "123456";
        String generatedCode = "123456";

        Mockito.when(otpCodeGenerator.generateCode(email)).thenReturn(generatedCode);

        assertDoesNotThrow(() -> mfaService.verifyOTPCode(email, validCode));
    }
}
