package com.meeth.mhgateway.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(CustomError.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(final CustomError customError) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", customError.getErrorMessage());
        errorResponse.put("errorCode", customError.getErrorCode());
        errorResponse.put("httpStatus", customError.getHttpStatus());
        return ResponseEntity.status(customError.getHttpStatus()).body(errorResponse);
    }


}
