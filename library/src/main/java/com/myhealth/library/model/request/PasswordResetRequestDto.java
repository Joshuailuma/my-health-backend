package com.myhealth.library.model.request;

import jakarta.validation.constraints.NotBlank;

import static com.myhealth.library.utils.Messages.EMAIL_REQUIRED;

public record PasswordResetRequestDto(
        @NotBlank(message = EMAIL_REQUIRED)
        String email
) {
}
