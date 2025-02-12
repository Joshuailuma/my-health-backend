package com.myhealth.library.model.request;

import jakarta.validation.constraints.NotBlank;

import static com.myhealth.library.utils.Messages.*;

public record ValidatePasswordResetDto(
        @NotBlank(message = EMAIL_REQUIRED)
        String email,

        @NotBlank(message = OTP_REQUIRED)
        String otp,

        @NotBlank(message = PASSWORD_REQUIRED)
        String newPassword
) {
}
