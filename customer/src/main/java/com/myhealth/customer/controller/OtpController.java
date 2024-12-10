package com.myhealth.customer.controller;

import com.myhealth.customer.service.OtpService;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.LoginResponseDto;
import com.myhealth.library.model.request.SendRegistrationDto;
import com.myhealth.library.model.response.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customer/otp/")
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService){
        this.otpService = otpService;
    }
    @PostMapping("/send-otp")
    public ResponseEntity<Mono<ApiResponseMessage>> sendOtp(@RequestBody SendRegistrationDto sendRegistrationDto) throws ApiError {

        Mono<ApiResponseMessage> responseMessage = otpService.sendRegistrationOtp(sendRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseMessage);
    }
}
