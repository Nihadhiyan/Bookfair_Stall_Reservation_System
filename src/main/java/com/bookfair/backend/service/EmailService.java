package com.bookfair.backend.service;

import java.util.Base64;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {
 
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String reservationDetails, String qrBase64) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Book Fair Reservation Confirmation");

            String content = "<h3>Reservation Details</h3>" +
                             "<p>" + reservationDetails + "</p>" +
                             "<br><img src='cid:qrCode' />";

            helper.setText(content, true);

            helper.addInline("qrCode", new ByteArrayResource(Base64.getDecoder().decode(qrBase64)), "image/png");

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}