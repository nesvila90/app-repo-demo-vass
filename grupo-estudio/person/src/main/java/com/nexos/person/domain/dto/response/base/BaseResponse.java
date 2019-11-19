package com.nexos.person.domain.dto.response.base;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BaseResponse<T> extends ResponseEntity<T> implements Serializable {

    private LocalDate timeRequest;
    private String message;
    private T response;
    private HttpStatus status;


    private BaseResponse(HttpStatus status) {
        super(status);
        this.status = status;
    }

    public static <T> BaseResponse<T> newBuilder(HttpStatus status) {
        return new BaseResponse<T>(status);
    }

    public BaseResponse<T> withMessage(final String message){
        this.message = message;
        return this;
    }

    public BaseResponse<T> withResponse(T response){
        this.response= response;
        return this;
    }

    public ResponseEntity<T> buildResponse(){
        return ResponseEntity.status(this.status).body(this.response);
    }

}
