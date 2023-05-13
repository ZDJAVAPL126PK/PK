package com.sda.clinicapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailsService {

    private final JavaMailSender emailSender;

    public void sendConfirmationCode(String receiver, String confirmationLink) {
        String subject = "Email confirmation.";
        String content = """
                Hello !
                                
                Please confirm your email by clicking in below link:
                %s
                """.formatted(confirmationLink);
        sendTextMessage(receiver, subject, content);
    }

    private void sendTextMessage(String receiver, String subject, String textContent) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("noreply@zdjava126pl.com");
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(textContent);

        emailSender.send(message);
    }

}
