package com.myhealth.customer.kafka;

import com.myhealth.customer.service.EmailService;
import com.myhealth.customer.service.impl.EmailServiceImpl;
import com.myhealth.library.model.request.OtpEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OtpEventConsumer {

private final EmailService emailService;

 public OtpEventConsumer(EmailService emailService){
     this.emailService = emailService;
 }

 @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}" )
    public void consumeOtpEvent(OtpEvent otpEvent){
     emailService.sendOtpEmail(otpEvent.getEmail(), otpEvent.getOtp(),
             "RegistrationOtpEmail.html","Registration OTP"
             );
     System.out.println("Consumed "+otpEvent);
    }
}
