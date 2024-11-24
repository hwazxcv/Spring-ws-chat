package com.stomp.chat.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;


@Getter
@Setter
public class CommonException extends RuntimeException{

    private HttpStatus status;
    public Errors errors;

    public CommonException(String message){
        this(message , HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public CommonException(String message , HttpStatus status){
        super(message);
        this.status = status;
    }

    public CommonException(Errors errors, HttpStatus status) {
        this.status = status;
        this.errors = errors;

    }
}
