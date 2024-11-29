package com.myhealth.customer.service;

import com.myhealth.library.model.request.SendRegistrationDto;
import com.myhealth.library.model.response.ApiResponseMessage;

public interface OtpService {
    ApiResponseMessage sendRegistrationOtp(SendRegistrationDto sendRegistrationDto);
}
