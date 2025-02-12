package com.myhealth.customer.controller;


import com.myhealth.customer.service.AuthenticationService;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.*;
import com.myhealth.library.model.response.ApiResponseMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "/customer/auth")
public class Authentication {
    private final AuthenticationService authenticationService;

    public Authentication(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<ApiResponseMessage<Object>>> register(@Valid @RequestBody RegistrationRequest registrationRequest) throws ApiError {
        return authenticationService.register(registrationRequest)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(response))
                .onErrorResume(ApiError.class, error -> Mono.just(
                        ResponseEntity.status(error.getHttpStatus())
                                .body(new ApiResponseMessage<>(error.getMessage(), null)
                                )));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws ApiError {

        LoginResponseDto responseMessage = authenticationService.login(loginRequestDto);
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Mono<ApiResponseMessage<Object>>> resetPassword(@Valid @RequestBody PasswordResetRequestDto passwordResetRequestDto) throws ApiError {
        Mono<ApiResponseMessage<Object>> responseMessage = authenticationService.resetPassword(passwordResetRequestDto);
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/validate-reset-password")
    public Mono<ResponseEntity<ApiResponseMessage<Object>>> validatePasswordReset(@Valid @RequestBody ValidatePasswordResetDto validatePasswordResetDto) throws ApiError {

        return authenticationService.validatePasswordReset(validatePasswordResetDto)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(response))
                .onErrorResume(ApiError.class, error -> Mono.just(
                        ResponseEntity.status(error.getHttpStatus())
                                .body(new ApiResponseMessage<>(error.getMessage(), null)
                                )));
    }
}