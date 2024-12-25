package com.stomp.chat.chat.exception;

import com.stomp.chat.common.utils.MessageUtils;
import com.stomp.chat.exception.CommonException;
import org.springframework.http.HttpStatus;



public class ChatRoomNotFoundException extends CommonException {

    public ChatRoomNotFoundException() {
        super(MessageUtils.getMessage("NotFound.chatRoom" , "errors") , HttpStatus.NOT_FOUND);

    }
}
