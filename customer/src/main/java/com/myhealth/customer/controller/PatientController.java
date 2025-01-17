package com.myhealth.customer.controller;

import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.RegistrationRequest;
import com.myhealth.library.model.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value = "/patient/api/v1")
//public class PatientController {
//
//
//    @PostMapping("/get-doctors")
//    public ResponseEntity<ApiResponseMessage> getDoctors(@RequestBody RegistrationRequest registrationRequest) throws ApiError {
//        ApiResponseMessage responseMessage = authenticationService.register(registrationRequest);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(responseMessage);
//    }
//}
