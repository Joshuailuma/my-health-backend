package com.myhealth.customer.controller;


import com.myhealth.customer.service.AuthenticationService;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.LoginRequestDto;
import com.myhealth.library.model.request.LoginResponseDto;
import com.myhealth.library.model.request.RegistrationRequest;
import com.myhealth.library.model.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/customer/api/v1")
public class Authentication {
private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponseMessage> register(@RequestBody RegistrationRequest registrationRequest) throws ApiError {
        ApiResponseMessage responseMessage = authenticationService.register(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws ApiError {

        LoginResponseDto responseMessage = authenticationService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseMessage);
    }
}
