package com.stomp.chat.chat.exception;


import com.stomp.chat.common.utils.MessageUtils;
import com.stomp.chat.exception.CommonException;
import org.springframework.http.HttpStatus;

public class ChatRoomDataException extends CommonException {

    public ChatRoomDataException() {
        super(MessageUtils.getMessage("NotFound.chatRoomData" , "errors"), HttpStatus.BAD_REQUEST);
    }
}
