package com.nexo.mfa.integration;

import com.nexo.mfa.service.MFAService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MFAControllerTest {
    @Autowired
    public MockMvc mockMvc;
    private static final String URL = "/api/v1/";

    @MockBean
    private MFAService mfaService;

    @Test
    void sendCodeReturnOk() throws Exception {
        doNothing().when(mfaService).sendOTPCode(any());

        mockMvc.perform(post(URL + "send-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Email sent successfully"));
    }

    @Test
    void verifyCodeReturnNoContent() throws Exception {
        doNothing().when(mfaService).verifyOTPCode(any(), any());

        mockMvc.perform(post(URL + "verify-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\",\"code\": \"123456\"}"))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @MethodSource("invalidSendCodeCases")
    void sendCodeReturnValidationError(String payload, String response) throws Exception {
        mockMvc.perform(post(URL + "send-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(response));
    }


    @ParameterizedTest
    @MethodSource("invalidVerifyCodeCases")
    void verifyCodeReturnValidationError(String payload, String response) throws Exception {
        mockMvc.perform(post(URL + "verify-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(response));
    }

    private static Stream<Arguments> invalidSendCodeCases(){
        return Stream.of(
                Arguments.of("{\"email\": \"\"}", "{\"message\":[\"Email could not be null or empty.\"]}"),
                Arguments.of("{\"email\": \"test\"}", "{\"message\":[\"Email address is not valid.\"]}")
        );
    }
    
    private static Stream<Arguments> invalidVerifyCodeCases(){
        return Stream.of(
                Arguments.of("{\"email\": \"\",\"code\": \"123456\"}", "{\"message\":[\"Email could not be null or empty.\"]}"),
                Arguments.of("{\"email\": \"test@example.com\",\"code\": \"\"}", "{\"message\":[\"Invalid code length.\",\"Code could not be null or empty.\"]}")
        );
    }
}
