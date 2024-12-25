package com.stomp.chat.chat.exception;


import com.stomp.chat.common.utils.MessageUtils;
import com.stomp.chat.exception.CommonException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CommonException {

    public UnauthorizedException(String message) {
        super(message , HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException() {
        super(MessageUtils.getMessage("Unauthorized" , "errors"),HttpStatus.UNAUTHORIZED);
    }
}
