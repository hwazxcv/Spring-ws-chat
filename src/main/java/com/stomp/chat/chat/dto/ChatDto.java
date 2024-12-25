package com.stomp.chat.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    public enum MessageType{
        ENTER , TALK , LEAVE
    }

    private MessageType type;
    private String roomNumber;
    private String sender;
    private String message;
    private String time;

}
