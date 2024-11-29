package com.myhealth.customer.service.impl;

import com.myhealth.customer.service.OtpService;
import com.myhealth.library.model.request.SendRegistrationDto;
import com.myhealth.library.model.response.ApiResponseMessage;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpService {
    @Override
    public ApiResponseMessage sendRegistrationOtp(SendRegistrationDto sendRegistrationDto) {
        return null;
    }
}
