package com.stomp.chat.chat.exception;


import com.stomp.chat.common.utils.MessageUtils;
import com.stomp.chat.exception.CommonException;
import org.springframework.http.HttpStatus;

public class ChatNotFoundException extends CommonException {

    public ChatNotFoundException(String message) {
        super(message , HttpStatus.NOT_FOUND);
    }

    public ChatNotFoundException(String message, HttpStatus status) {
        super(MessageUtils.getMessage("NotFound.Chat" , "errors"),HttpStatus.NOT_FOUND);
    }
}

