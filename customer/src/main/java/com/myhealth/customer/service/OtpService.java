package com.myhealth.customer.service;

import com.myhealth.library.model.request.SendRegistrationDto;
import com.myhealth.library.model.response.ApiResponseMessage;
import reactor.core.publisher.Mono;

public interface OtpService {
   Mono<ApiResponseMessage> sendRegistrationOtp(SendRegistrationDto sendRegistrationDto);
}
