package com.myhealth.library.exception;

import com.myhealth.library.model.response.ApiExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;


@ControllerAdvice
public class ApiExceptionHandler  {


    @ExceptionHandler(ApiError.class)
    public ResponseEntity<ApiExceptionMessage> handleCustomException(final ApiError e) {
        ApiExceptionMessage errorResponse = new ApiExceptionMessage(e.getMessage(), "001", e.getHttpStatus());
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiExceptionMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ApiExceptionMessage errorResponse = new ApiExceptionMessage(e.getMessage(), "002", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e) {
        ApiExceptionMessage errorResponse = new ApiExceptionMessage(e.getMessage(), "005", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        ApiExceptionMessage errorResponse = new ApiExceptionMessage(e.getMessage(), "006", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e) {
        ApiExceptionMessage errorResponse = new ApiExceptionMessage(e.getMessage(), "007", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<Object> handleInputException(ServerWebInputException e) {
        ApiExceptionMessage errorResponse = new ApiExceptionMessage(e.getMessage(), "008", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
