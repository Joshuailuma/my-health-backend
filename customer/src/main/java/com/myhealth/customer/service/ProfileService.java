package com.myhealth.customer.service;

import com.myhealth.library.model.request.EditProfileRequest;
import com.myhealth.library.model.response.ApiResponseMessage;
import com.myhealth.customer.entity.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ProfileService {
    Mono<ApiResponseMessage<UserDetails>> viewProfile(String id);
    ApiResponseMessage<String> uploadProfilePhoto(MultipartFile multipartFile);
    ApiResponseMessage<String> editProfile(EditProfileRequest request);
}
