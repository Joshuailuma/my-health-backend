package com.myhealth.customer.service.impl;

import com.myhealth.customer.service.EmailService;
import com.myhealth.customer.service.ThymeleafService;
import com.myhealth.library.exception.ApiError;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService    {
    @Value("${spring.mail.username}")
    private String email;
    private final JavaMailSender javaMailSender;
    private final ThymeleafService thymeleafService;

    EmailServiceImpl(JavaMailSender javaMailSender,
                     ThymeleafService thymeleafService){
        this.javaMailSender = javaMailSender;
        this.thymeleafService = thymeleafService;
    }

    public void sendOtpEmail(String email, String otp) {

        Map<String, Object> contextForTemplate = new HashMap<>();
        contextForTemplate.put("Username", "OTP");

        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            mimeMessageHelper.setFrom("joshuailuma@gmail.com");
            mimeMessageHelper.setText(thymeleafService.createContent("RegistrationOtpEmail.html",  contextForTemplate), true);
            mimeMessageHelper.setTo("joshuailumalt@gmail.com");
            mimeMessageHelper.setSubject("Registration OTP");
            javaMailSender.send(message);

        } catch(Exception e){
            System.out.println("Error sending mail "+e.getMessage());
            throw new ApiError(e.getMessage(), "007", HttpStatus.BAD_REQUEST);

        }

        // JavaMailSender
        System.out.printf("Sending OTP %s to email %s%n", otp, email);
    }

}
