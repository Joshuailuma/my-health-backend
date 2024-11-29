package com.myhealth.library.model.request;

import jakarta.validation.constraints.NotBlank;

import static com.myhealth.library.utils.Messages.USER_ID_REQUIRED;

public class SendRegistrationDto {
    @NotBlank(message = USER_ID_REQUIRED)
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
