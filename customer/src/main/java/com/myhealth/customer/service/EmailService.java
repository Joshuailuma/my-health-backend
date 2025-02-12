package com.myhealth.customer.service;

import reactor.core.publisher.Mono;

public interface EmailService {
    Mono<Void> sendOtpEmail(String recipientEmail, String otp,
                            String emailTemplate,
                            String subject);
}
