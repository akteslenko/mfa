package com.nexo.mfa.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class MFAController {
    @PostMapping("/send-code")
    public String sendCode() {
        return "This is the information you requested.";
    }

    @GetMapping("/verify-code")
    public String verifyCode(@RequestParam String name) {
        return "Hello, " + name + "!";
    }
}
