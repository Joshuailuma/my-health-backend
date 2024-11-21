package com.myhealth.customer.service;


import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.LoginRequestDto;
import com.myhealth.library.model.request.LoginResponseDto;
import com.myhealth.library.model.request.RegistrationRequest;
import com.myhealth.library.model.response.ApiResponseMessage;

public interface AuthenticationService {
    ApiResponseMessage register(RegistrationRequest registrationRequest) throws ApiError;
    LoginResponseDto login(LoginRequestDto loginRequestDto) throws ApiError;
}
