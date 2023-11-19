package com.nexo.mfa.unit.util;

import com.nexo.mfa.util.TimeProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeProviderTest {

    @Mock
    private TimeProvider timeProvider;

    @Test
    void getCurrentEpochSecond_shouldReturnCurrentTime() {
        long currentTimeMillis = 1234560800L;
        when(timeProvider.getCurrentEpochSecond()).thenReturn(currentTimeMillis);

        long result = timeProvider.getCurrentEpochSecond();

        assertEquals(currentTimeMillis, result);
    }
}
