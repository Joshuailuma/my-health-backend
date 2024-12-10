package com.myhealth.customer.controller;

import com.myhealth.customer.service.ProfileService;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.response.ApiResponseMessage;
import com.myhealth.customer.entity.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;
    ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @GetMapping("/view")
    public ResponseEntity<ApiResponseMessage<UserDetails>> viewProfile(@PathVariable String id) throws ApiError {
        ApiResponseMessage<UserDetails> response = profileService.viewProfile(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/upload-image")
    public ResponseEntity<ApiResponseMessage<UserDetails>> uploadImage(@PathVariable String id) throws ApiError {
        ApiResponseMessage<UserDetails> response = profileService.viewProfile(id);
        return ResponseEntity.ok(response);
    }
}
