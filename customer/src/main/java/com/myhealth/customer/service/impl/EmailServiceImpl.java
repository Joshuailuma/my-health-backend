package com.myhealth.customer.service.impl;

import com.myhealth.customer.config.MailSenderConfig;
import com.myhealth.customer.service.EmailService;
import com.myhealth.customer.service.ThymeleafService;
import com.myhealth.library.exception.ApiError;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService    {
    @Value("${spring.mail.username}")
    private String username;
    private final MailSenderConfig javaMailSender;
    private final ThymeleafService thymeleafService;

    EmailServiceImpl(MailSenderConfig javaMailSender,
                     ThymeleafService thymeleafService){
        this.javaMailSender = javaMailSender;
        this.thymeleafService = thymeleafService;
    }

    public Mono<Void> sendOtpEmail(String recipientMail,
                                   String otp,
                                   String emailTemplate,
                                   String subject) {

        Map<String, Object> contextForTemplate = new HashMap<>();


        contextForTemplate.put("otp", otp);
        contextForTemplate.put("username", recipientMail);

        try{
            MimeMessage message = javaMailSender.getJavaMailSender().createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setText(thymeleafService.createContent(emailTemplate,  contextForTemplate), true);
            mimeMessageHelper.setTo(recipientMail);
            mimeMessageHelper.setSubject(subject);
            javaMailSender.getJavaMailSender().send(message);

        } catch(Exception e){
            System.out.println("Error sending mail "+e.getMessage());
            throw new ApiError(e.getMessage(), "007", HttpStatus.BAD_REQUEST);

        }

        // JavaMailSender
        System.out.printf("Sending OTP %s to email %s%n", otp, recipientMail);
        return Mono.empty();
    }

}
