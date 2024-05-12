package com.meeth.mhclient.controller;


import com.meeth.mhclient.exception.CustomError;
import com.meeth.mhclient.model.request.LoginRequestDto;
import com.meeth.mhclient.model.request.LoginResponseDto;
import com.meeth.mhclient.model.request.RegistrationRequest;
import com.meeth.mhclient.model.response.CustomResponseMessage;
import com.meeth.mhclient.service.AuthenticationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthenticationController {

   private final AuthenticationService authenticationService;

        @PostMapping("/register")
        public ResponseEntity<CustomResponseMessage> register(@RequestBody RegistrationRequest registrationRequest) throws CustomError {
            CustomResponseMessage responseMessage =  authenticationService.register(registrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(responseMessage);

        }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws CustomError {
        LoginResponseDto responseMessage =  authenticationService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseMessage);
    }
}
