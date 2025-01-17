package com.myhealth.customer.controller;

import com.myhealth.customer.service.ProfileService;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.EditProfileRequest;
import com.myhealth.library.model.response.ApiResponseMessage;
import com.myhealth.customer.entity.UserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/customer/profile")
public class ProfileController {

    private final ProfileService profileService;
    ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Mono<ApiResponseMessage<UserDetails>>> viewProfile(@PathVariable("id") String id) throws ApiError {
        Mono<ApiResponseMessage<UserDetails>> response = profileService.viewProfile(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<ApiResponseMessage<String>> uploadImage(@RequestParam MultipartFile file) throws ApiError {
        ApiResponseMessage<String> response = profileService.uploadProfilePhoto(file);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<ApiResponseMessage<String>> editProfile(@Valid @RequestBody EditProfileRequest request) throws ApiError {
        ApiResponseMessage<String> response = profileService.editProfile(request);
        return ResponseEntity.ok(response);
    }
}
