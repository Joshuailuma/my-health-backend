package com.myhealth.library.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiExceptionMessage {
    private String message;
    private String code;
    private HttpStatus httpStatus;
}
