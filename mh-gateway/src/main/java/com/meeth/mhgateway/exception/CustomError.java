package com.meeth.mhgateway.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomError extends Exception{
    private final String errorMessage;
    private final String errorCode;
    private HttpStatus httpStatus;


    public CustomError(String errorMessage, String errorCode, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
    @Override
    public String getMessage() {
        // Return a JSON representation of the error details
        return String.format("{\"errorMessage\":\"%s\",\"errorCode\":\"%s\", \"httpStatus\":\"%s\"}",
                errorMessage, errorCode, httpStatus);
    }

}
