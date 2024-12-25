package com.stomp.chat.chat;

import lombok.Data;

@Data
public class SearchRoomData {

    private int page = 1;
    private int limit = 20;


    private String roomName;

}
