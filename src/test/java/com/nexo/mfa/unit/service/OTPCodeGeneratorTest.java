package com.nexo.mfa.unit.service;

import com.nexo.mfa.service.OTPCodeGenerator;
import com.nexo.mfa.util.TimeProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.Mac;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OTPCodeGeneratorTest {

    @Mock
    private Mac hmac;

    @InjectMocks
    private OTPCodeGenerator otpCodeGenerator;

    @Mock
    private TimeProvider timeProvider;

    @Test
    void generateCodeGenerateCorrectOTPCode() {
        String key = "yourSecretKey";
        long currentTime = 123456789L;

        when(timeProvider.getCurrentEpochSecond()).thenReturn(currentTime);

        ReflectionTestUtils.setField(otpCodeGenerator, "TOTP_DIGITS", 6);
        ReflectionTestUtils.setField(otpCodeGenerator, "TOTP_PERIOD", 60);

        String otpCode = otpCodeGenerator.generateCode(key);

        assertEquals("888175", otpCode);
    }
}
