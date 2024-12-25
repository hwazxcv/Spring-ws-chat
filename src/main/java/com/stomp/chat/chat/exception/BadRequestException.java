package com.stomp.chat.chat.exception;


import com.stomp.chat.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public class BadRequestException extends CommonException {


    public BadRequestException(String message) {
        super(message , HttpStatus.BAD_REQUEST);
    }

    public BadRequestException (Errors errors){
       super(errors , HttpStatus.BAD_REQUEST);

    }
}
