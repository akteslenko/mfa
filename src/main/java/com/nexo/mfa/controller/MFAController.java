package com.nexo.mfa.controller;

import com.nexo.mfa.request.SendCodeRequest;
import com.nexo.mfa.request.VerifyCodeRequest;
import com.nexo.mfa.service.MFAService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@Validated
public class MFAController {
    private final MFAService mfaService;

    public MFAController(MFAService mfaService) {
        this.mfaService = mfaService;
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(@Valid @RequestBody SendCodeRequest sendCodeRequest) {
        this.mfaService.sendOTPCode(sendCodeRequest.getEmail());

        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@Valid @RequestBody VerifyCodeRequest verifyCodeRequest) {
        this.mfaService.verifyOTPCode(verifyCodeRequest.getEmail(), verifyCodeRequest.getCode());

        return ResponseEntity.noContent().build();
    }
}
