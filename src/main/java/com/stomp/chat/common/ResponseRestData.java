package com.stomp.chat.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRestData<T> {

    private boolean success=true;
    private HttpStatus status = HttpStatus.OK;
    private T data;
    private Object message;



    public ResponseRestData(T data , Object message){
        this.data = data;
        this.message = message;
    }
}
