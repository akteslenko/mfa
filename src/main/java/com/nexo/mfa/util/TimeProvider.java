package com.nexo.mfa.util;

import org.springframework.stereotype.Component;

@Component
public class TimeProvider {
    public long getCurrentEpochSecond() {
        return System.currentTimeMillis() / 1000;
    }
}
