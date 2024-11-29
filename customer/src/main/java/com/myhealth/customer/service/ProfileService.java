package com.myhealth.customer.service;

import com.myhealth.library.model.response.ApiResponseMessage;
import com.myhealth.customer.entity.UserDetails;

public interface ProfileService {
    ApiResponseMessage<UserDetails> viewProfile(String id);
}
