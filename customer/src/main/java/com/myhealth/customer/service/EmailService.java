package com.myhealth.customer.service;

public interface EmailService {
    public void sendOtpEmail(String recipientEmail, String otp,
                             String emailTemplate,
                             String subject);
}
