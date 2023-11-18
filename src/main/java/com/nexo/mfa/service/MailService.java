package com.nexo.mfa.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nexohome77@gmail.com");
        message.setTo(email);
        message.setSubject("MFA-code");
        message.setText("This is your code: " + code);

        this.mailSender.send(message);
    }
}
