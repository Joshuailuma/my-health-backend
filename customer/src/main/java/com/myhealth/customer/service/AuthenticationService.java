package com.myhealth.customer.service;


import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.*;
import com.myhealth.library.model.response.ApiResponseMessage;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<ApiResponseMessage<Object>> register(RegistrationRequest registrationRequest) throws ApiError;
    LoginResponseDto login(LoginRequestDto loginRequestDto) throws ApiError;
    Mono<ApiResponseMessage<Object>> resetPassword(PasswordResetRequestDto passwordResetRequestDto) throws ApiError;
    Mono<ApiResponseMessage<Object>> validatePasswordReset(ValidatePasswordResetDto validatePasswordResetDto);
}
