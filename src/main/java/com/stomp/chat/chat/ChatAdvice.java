package com.stomp.chat.chat;

import com.stomp.chat.common.ResponseRestData;
import com.stomp.chat.common.utils.MessageUtils;
import com.stomp.chat.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice("com.stomp.chat")
@RequiredArgsConstructor
public class ChatAdvice {

    private final MessageUtils messageUtil;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseRestData> errorHandler(Exception e ){

        HttpStatus status =  HttpStatus.INTERNAL_SERVER_ERROR;
        Object message = e.getMessage();

        if(e instanceof CommonException commonException){
            status = commonException.getStatus();
            Errors errors = commonException.getErrors();

            if(errors !=null){
                Map<String , List<String>> messages = messageUtil.getErrorMessages(errors);
                if(messages != null && !messages.isEmpty()) message = messages;
            }
        }else {
            //message= ProcessUtils.getMessage("Internal","errors");
        }
        e.printStackTrace();

        ResponseRestData data = new ResponseRestData();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(message);


        return ResponseEntity.status(status).body(data);
    }

}
