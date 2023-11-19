package com.nexo.mfa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.Instant;

@Service
public class OTPCodeGenerator {
    private static final String ALGORITHM = "HmacSHA1";

    @Value("${spring.otp.totp-digits}")
    private int TOTP_DIGITS;

    @Value("${spring.otp.totp-period}")
    private int TOTP_PERIOD;

    public String generateCode(String key) {
        long time = Instant.now().getEpochSecond() / TOTP_PERIOD;
        byte[] hmacHash = hmacHash(key, time);

        return otpCode(hmacHash);
    }

    private byte[] hmacHash(String key, long time) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] timeBytes = ByteBuffer.allocate(8).putLong(time).array();

            Mac hmac = Mac.getInstance(ALGORITHM);
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, ALGORITHM);
            hmac.init(macKey);
            return hmac.doFinal(timeBytes);
        } catch (GeneralSecurityException exception) {
            throw new UndeclaredThrowableException(exception);
        }
    }

    private String otpCode(byte[] hmacHash) {
        int offset = hmacHash[hmacHash.length - 1] & 0xF;
        int truncatedHash = (hmacHash[offset] & 0x7f) << 24 | (hmacHash[offset + 1] & 0xff) << 16 | (hmacHash[offset + 2] & 0xff) << 8 | (hmacHash[offset + 3] & 0xff);
        long otpCode = truncatedHash % (long) Math.pow(10, TOTP_DIGITS);

        return String.valueOf(otpCode);
    }
}
