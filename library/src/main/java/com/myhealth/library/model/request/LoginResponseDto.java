package com.myhealth.library.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String refreshExpiresIn;
}
