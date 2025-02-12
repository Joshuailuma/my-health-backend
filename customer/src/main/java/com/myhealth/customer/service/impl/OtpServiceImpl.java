package com.myhealth.customer.service.impl;

import com.myhealth.customer.repository.UserRepository;
import com.myhealth.customer.service.OtpService;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.OtpEvent;
import com.myhealth.library.model.request.SendRegistrationDto;
import com.myhealth.library.model.response.ApiResponseMessage;
import com.myhealth.library.utils.AppUtils;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class OtpServiceImpl implements OtpService {
    private final KafkaTemplate<String, OtpEvent> kafkaTemplate;
    private final UserRepository userRepository;

    public OtpServiceImpl(KafkaTemplate<String, OtpEvent> kafkaTemplate, UserRepository userRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<ApiResponseMessage> sendRegistrationOtp(SendRegistrationDto sendRegistrationDto) {
        return userRepository.findById(UUID.fromString(sendRegistrationDto.getUserId()))
                .flatMap(user -> {
                    String otp = AppUtils.generateOtp();
                    OtpEvent otpEvent = new OtpEvent(user.getEmail(), otp);
                    kafkaTemplate.send("otp-topic", otpEvent);
                    return Mono.just(new ApiResponseMessage("OTP sent successfully", null));
                })
                .onErrorResume(e -> Mono.error(new ApiError(e.getMessage(), "006", HttpStatus.BAD_REQUEST)));
    }

}
