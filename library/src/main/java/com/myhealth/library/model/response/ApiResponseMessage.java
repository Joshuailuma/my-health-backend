package com.myhealth.library.model.response;

import com.myhealth.library.exception.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Mono;


public class ApiResponseMessage<T> {
    private String message;
    private T data;
    private Mono<T> dataRetrieved;

    public ApiResponseMessage(String message, T data){
        this.message = message;
        this.data = data;
    }

    public ApiResponseMessage(){
    }

    public ApiResponseMessage(String message, Mono<T> dataRetrieved) {
        this.message = message;
        this.dataRetrieved = dataRetrieved;
    }

    public String getMessage(){
        return message;
    }

    public T getData() {
        return data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setDataRetrieved(Mono<T> dataRetrieved) {
        this.dataRetrieved = dataRetrieved;
    }

    public Mono<T> getDataRetrieved() {
        return dataRetrieved;
    }
}
