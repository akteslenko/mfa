package com.nexo.mfa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail-sender.from}")
    private String from;

    @Value("${spring.mail-sender.subject}")
    private String subject;

    @Value("${spring.mail-sender.text}")
    private String text;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.from);
        message.setTo(email);
        message.setSubject(this.subject);
        message.setText(this.text + code);
        this.mailSender.send(message);
    }
}
