package com.myhealth.library.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiError extends RuntimeException{
    private final String errorMessage;
    private final String errorCode;
    private HttpStatus httpStatus;


    public ApiError(String errorMessage, String errorCode, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
